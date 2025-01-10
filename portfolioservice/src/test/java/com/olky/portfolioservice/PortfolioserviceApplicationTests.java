package com.olky.portfolioservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;



@SpringBootTest
class PortfolioserviceApplicationTests {

	@Mock
	private RestTemplate restTemplate;

	@MockitoBean
	private ExchangeRateServiceClient exchangeRateServiceClient;


	@Autowired
	private PortfolioService portfolioService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Initialise les mocks
	}

	@Test
	void contextLoads() {
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

		System.out.println("Portfolio valuation: "+ result);

        // Vérification
        assertEquals(138360.499682, result, 0.0001); // Tolérance pour les flottants
        verify(exchangeRateServiceClient, times(1)).getExchangeRate("bitcoin", baseCurrency);
        verify(exchangeRateServiceClient, times(1)).getExchangeRate("dai", baseCurrency);
    }

	@Test
	void testGetPortfolioValuation_EmptyHoldings() {
		// Données de test
		Portfolio portfolio = new Portfolio();
		portfolio.setId(1L);
		portfolio.setName("Crypto Wallet");
		portfolio.setHoldings(List.of());

		String baseCurrency = "usd";

		// Appel réel
		double result = portfolioService.getPortfolioValuation(portfolio, baseCurrency);

		// Vérification
		assertEquals(0.0, result, 0.0001); // Tolérance pour les flottants
	}
	
	@Test
	void testGetPortfolioValuation_NullHoldings() {
		// Données de test
		Portfolio portfolio = new Portfolio();
		portfolio.setId(1L);
		portfolio.setName("Crypto Wallet");
		portfolio.setHoldings(null);

		String baseCurrency = "usd";

		// Appel réel
		double result = portfolioService.getPortfolioValuation(portfolio, baseCurrency);

		// Vérification
		assertEquals(0.0, result, 0.0001); // Tolérance pour les flottants
	}

	@Test
	void testGetPortfolioValuation_NullBaseCurrency() {
		// Données de test
		Portfolio portfolio = new Portfolio();
		portfolio.setId(1L);
		portfolio.setName("Crypto Wallet");
		portfolio.setHoldings(List.of(
			new Holding(1L, "bitcoin", 1.5),
			new Holding(2L, "dai", 2.0)
		));

		String baseCurrency = null;

		// Appel réel
		double result = portfolioService.getPortfolioValuation(portfolio, baseCurrency);

		// Vérification
		assertEquals(0.0, result, 0.0001); // Tolérance pour les flottants
	}

	@Test
	void testGetPortfolioValuation_EmptyBaseCurrency() {
		// Données de test
		Portfolio portfolio = new Portfolio();
		portfolio.setId(1L);
		portfolio.setName("Crypto Wallet");
		portfolio.setHoldings(List.of(
			new Holding(1L, "bitcoin", 1.5),
			new Holding(2L, "dai", 2.0)
		));

		String baseCurrency = "";

		// Appel réel
		double result = portfolioService.getPortfolioValuation(portfolio, baseCurrency);

		// Vérification
		assertEquals(0.0, result, 0.0001); // Tolérance pour les flottants
	}

	@Test
	void testGetPortfolioValuation_NullPortfolio() {
		// Données de test
		Portfolio portfolio = null;
		String baseCurrency = "usd";

		// Appel réel
		double result = portfolioService.getPortfolioValuation(portfolio, baseCurrency);

		// Vérification
		assertEquals(0.0, result, 0.0001); // Tolérance pour les flottants
	}

	@Test
	void testGetPortfolioValuation_NullPortfolioAndBaseCurrency() {
		// Données de test
		Portfolio portfolio = null;
		String baseCurrency = null;

		// Appel réel
		double result = portfolioService.getPortfolioValuation(portfolio, baseCurrency);

		// Vérification
		assertEquals(0.0, result, 0.0001); // Tolérance pour les flottants
	}

	@Test
	void testGetPortfolioValuation_EmptyPortfolioAndBaseCurrency() {
		// Données de test
		Portfolio portfolio = new Portfolio();
		portfolio.setId(1L);
		portfolio.setName("Crypto Wallet");
		portfolio.setHoldings(List.of());

		String baseCurrency = "";

		// Appel réel
		double result = portfolioService.getPortfolioValuation(portfolio, baseCurrency);

		// Vérification
		assertEquals(0.0, result, 0.0001); // Tolérance pour les flottants
	}

	@Test
	void testGetPortfolioValuation_EmptyPortfolio() {
		// Données de test
		Portfolio portfolio = new Portfolio();
		portfolio.setId(1L);
		portfolio.setName("Crypto Wallet");
		portfolio.setHoldings(List.of());

		String baseCurrency = "usd";

		// Appel réel
		double result = portfolioService.getPortfolioValuation(portfolio, baseCurrency);

		// Vérification
		assertEquals(0.0, result, 0.0001); // Tolérance pour les flottants
	}

	@Test
	void testGetPortfolioValuation_NullBaseCurrencyAndEmptyPortfolio() {
		// Données de test
		Portfolio portfolio = new Portfolio();
		portfolio.setId(1L);
		portfolio.setName("Crypto Wallet");
		portfolio.setHoldings(List.of());

		String baseCurrency = null;

		// Appel réel
		double result = portfolioService.getPortfolioValuation(portfolio, baseCurrency);

		// Vérification
		assertEquals(0.0, result, 0.0001); // Tolérance pour les flottants
	}

	
}
