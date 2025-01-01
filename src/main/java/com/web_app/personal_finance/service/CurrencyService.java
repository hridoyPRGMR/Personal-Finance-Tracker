package com.web_app.personal_finance.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

    @Value("${api.key.exchangerate}")
    private String apiKey;

    private final String BASE_API_URL = "https://v6.exchangerate-api.com/v6/";

    public Map<String, Double> getExchangeRate(String baseCurrency) {
       
        String apiUrl = BASE_API_URL + apiKey + "/latest/" + baseCurrency;
        RestTemplate restTemplate = new RestTemplate();
        
        try {
            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
            return (Map<String, Double>) response.get("conversion_rates");
        } 
        catch (Exception e) {
            throw new RuntimeException("Failed to fetch exchange rate", e);
        }
    }
}

