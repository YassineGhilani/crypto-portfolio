package com.olky.exchangerateservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRateService {
    @Autowired
    private final RestTemplate restTemplate;

    public ExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExchangeRate fetchFromExternalApi(String currency, String base) {
         // Validation des paramètres
         if (currency == null || currency.isEmpty() || base == null || base.isEmpty()) {
            throw new IllegalArgumentException("Les paramètres 'currency' et 'base' ne peuvent pas être vides");
        }

        // Construction de l'URL de l'API CoinGecko
        String url = String.format("https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=%s", 
                                   currency.toLowerCase(), base.toLowerCase());

        try {
            // Appel de l'API et récupération de la réponse
            Map<String, Map<String, Double>> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey(currency.toLowerCase())) {
                Number rateNumber = response.get(currency.toLowerCase()).get(base.toLowerCase());
        
        // Convertir la valeur en double (que ce soit Integer, Double ou autre type numérico)
                double rate = rateNumber.doubleValue();
                return new ExchangeRate(currency, base, rate);
            } else {
                throw new RuntimeException("Impossible de trouver le taux de change pour la paire de devises donnée");
            }
        } catch (Exception e) {
            // Capture d'exceptions et retour d'une erreur plus précise
            throw new RuntimeException("Erreur lors de la récupération du taux de change depuis l'API : " + e.getMessage(), e);
        }
    }

}
