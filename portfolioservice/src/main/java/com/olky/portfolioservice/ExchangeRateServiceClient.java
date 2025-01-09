package com.olky.portfolioservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
        try {
            return restTemplate.getForObject(url, Double.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching exchange rate");
        }
    }
}
