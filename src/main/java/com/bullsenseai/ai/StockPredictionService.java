package com.bullsenseai.ai;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class StockPredictionService {

    private final String apiUrl = "http://localhost:5000/predict";  // Flask API URL

    public String predict(String stockSymbol) {
        try {
            // For simplicity, let's assume the stock value is based on the stock symbol
            // You can replace this with more complex logic to fetch actual stock data
            double stockValue = getStockValue(stockSymbol);  // Example: Convert stock symbol to numeric value

            // Call the Python API to get the prediction
            RestTemplate restTemplate = new RestTemplate();

            // Prepare the JSON payload with stock value
            String jsonPayload = "{\"stock_value\": " + stockValue + "}";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

            // Make the request to the Flask API
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

            // Extract and return the prediction from the response
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error in prediction: " + e.getMessage();
        }
    }

    // Example function to simulate stock value based on symbol
    private double getStockValue(String stockSymbol) {
        switch (stockSymbol) {
            case "AAPL":
                return 145.09;
            case "GOOG":
                return 2725.60;
            default:
                return 100.0;  // Default value
        }
    }
}
