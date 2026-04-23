package com.shopease.service;

import com.shopease.model.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroqService {

    @Value("${groq.api.key:}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.api.model}")
    private String model;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getRecommendations(String userQuery, List<Product> inventory) {
        if (apiKey == null || apiKey.isBlank()) {
            return "⚠️ Groq API key is not configured. Please set the GROQ_API_KEY environment variable.";
        }

        try {
            // Build inventory context
            StringBuilder inventoryJson = new StringBuilder("[");
            for (int i = 0; i < inventory.size(); i++) {
                Product p = inventory.get(i);
                if (i > 0) inventoryJson.append(",");
                inventoryJson.append(String.format(
                        "{\"id\":%d,\"name\":\"%s\",\"description\":\"%s\",\"price\":%.2f,\"stock\":%d}",
                        p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getStockQuantity()
                ));
            }
            inventoryJson.append("]");

            String systemPrompt = "You are a smart shopping assistant for ShopEase, an online electronics and lifestyle store. "
                    + "The user will ask you for product recommendations or advice. "
                    + "Here is the current store inventory in JSON format:\n" + inventoryJson
                    + "\n\nBased on this inventory, provide helpful, concise recommendations. "
                    + "Always reference specific product names and their prices from the inventory when relevant. "
                    + "Format your response nicely with product names in bold using **productName** markdown syntax. "
                    + "Keep responses under 200 words.";

            // Build request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);

            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> systemMsg = new HashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content", systemPrompt);
            messages.add(systemMsg);

            Map<String, String> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", userQuery);
            messages.add(userMsg);

            requestBody.put("messages", messages);
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 500);

            String jsonBody = objectMapper.writeValueAsString(requestBody);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                JsonNode choices = root.get("choices");
                if (choices != null && choices.isArray() && choices.size() > 0) {
                    return choices.get(0).get("message").get("content").asText();
                }
                return "No recommendations available at this time.";
            } else {
                JsonNode errorRoot = objectMapper.readTree(response.body());
                String errorMsg = errorRoot.has("error")
                        ? errorRoot.get("error").get("message").asText()
                        : "Unknown error";
                return "⚠️ Groq API error (" + response.statusCode() + "): " + errorMsg;
            }

        } catch (Exception e) {
            return "⚠️ Error connecting to Groq AI: " + e.getMessage();
        }
    }
}
