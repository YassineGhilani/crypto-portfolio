package com.olky.portfolioservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ExchangeRateServiceClient {

    private final RestTemplate restTemplate;
    
    @Value("${exchange.rate.service.url}")
    private String exchangeRateServiceUrl;

    public ExchangeRateServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double getExchangeRate(String symbol, String baseCurrency) {
        String url = String.format(
                "%s/exchange-rate?currency=%s&base=%s", 
                        exchangeRateServiceUrl, 
                        symbol, 
                        baseCurrency);
        
        System.out.println("Calling ExchangeRateService with URL: " + url);
                        
        try {
            String rawResponse = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode response = mapper.readTree(rawResponse);
            if (response != null && response.has("currency")) {
                JsonNode currencyNode = response.get("rate");
                if (currencyNode.isNumber()) {
                    return currencyNode.asDouble();
                }
            }

            if (response == null || !response.has(symbol.toLowerCase())) {
                throw new RuntimeException("Invalid response: " + rawResponse);
            }

            throw new RuntimeException("Exchange rate not found in response for: " + symbol);
        } catch (Exception e) {
            e.printStackTrace(); // Log complet de l'exception
            throw new RuntimeException("Error while fetching exchange rate: "+ e.getMessage());
        }
    }
}
