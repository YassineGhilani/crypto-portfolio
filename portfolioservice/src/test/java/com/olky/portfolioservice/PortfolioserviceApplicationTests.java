package com.olky.portfolioservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;


@SpringBootTest
class PortfolioserviceApplicationTests {

	@Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExchangeRateServiceClient exchangeRateServiceClient;


    @InjectMocks
    private PortfolioService portfolioService;

	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks
    }

	@Test
	void contextLoads() {
	}

	 @Test
    void testGetExchangeRate_Success() {
        String symbol = "bitcoin";
        String baseCurrency = "usd";
        String url = String.format("https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=%s", 
                                   symbol.toLowerCase(), baseCurrency.toLowerCase());
		JsonNode response = restTemplate.getForObject(url, JsonNode.class);

        // Mock de la réponse
        when(response).thenReturn(response);

        // Appel réel
        Double result = exchangeRateServiceClient.getExchangeRate(symbol, baseCurrency);

        // Vérification
        assertEquals(92239.0, result);
        verify(restTemplate, times(1)).getForObject(url, Double.class);
    }

    @Test
    void testGetExchangeRate_Failure() {
        String symbol = "bitcoin";
        String baseCurrency = "usd";
        String url = String.format("https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=%s", 
                                   symbol.toLowerCase(), baseCurrency.toLowerCase());

        // Simule une exception
		JsonNode response = restTemplate.getForObject(url, JsonNode.class);
        
        when(response).thenThrow(new RuntimeException("API error"));

        // Appel réel
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> exchangeRateServiceClient.getExchangeRate(symbol, baseCurrency));

        // Vérification du message
        assertEquals("Error while fetching exchange rate: API error", exception.getMessage());
        verify(restTemplate, times(1)).getForObject(url, Double.class);
    }

	@Test
    void testGetPortfolioValuation() {
        // Données de test
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setName("Crypto Wallet");
        portfolio.setHoldings(List.of(
            new Holding(1L, "bitcoin", 1.5),
            new Holding(2L, "dai", 2.0)
        ));

        String baseCurrency = "usd";

        // Mock des taux de change
        when(exchangeRateServiceClient.getExchangeRate("bitcoin", baseCurrency)).thenReturn(92239.0);
        when(exchangeRateServiceClient.getExchangeRate("dai", baseCurrency)).thenReturn(0.999841);

        // Appel réel
        double result = portfolioService.getPortfolioValuation(portfolio, baseCurrency);

        // Vérification
        assertEquals(138358.681682, result, 0.0001); // Tolérance pour les flottants
        verify(exchangeRateServiceClient, times(1)).getExchangeRate("bitcoin", baseCurrency);
        verify(exchangeRateServiceClient, times(1)).getExchangeRate("dai", baseCurrency);
    }

}
