package com.olky.portfolioservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class ExchangeRateServiceClient {

    private final RestTemplate restTemplate;
    
    //@Value("${exchange.rate.service.url}")
    //private String exchangeRateServiceUrl;

    public ExchangeRateServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double getExchangeRate(String symbol, String baseCurrency) {
        // String url = String.format(
        //         "%s/exchange-rate?currency=%s&base=%s", 
        //                 exchangeRateServiceUrl, 
        //                 symbol, 
        //                 baseCurrency);
        String url = String.format("https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=%s", 
                        symbol.toLowerCase(), baseCurrency.toLowerCase());
        System.out.println("Calling ExchangeRateService with URL: " + url);
                        
        try {
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);
            if (response != null && response.has(symbol.toLowerCase())) {
                JsonNode currencyNode = response.get(symbol.toLowerCase()).get(baseCurrency.toLowerCase());
                if (currencyNode.isNumber()) {
                    return currencyNode.asDouble();
                }
            }
            throw new RuntimeException("Exchange rate not found in response for: " + symbol);
        } catch (Exception e) {
            e.printStackTrace(); // Log complet de l'exception
            throw new RuntimeException("Error while fetching exchange rate: "+ e.getMessage());
        }
    }
}
