package com.example.Flashcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// This record helps Jackson map the JSON data
record AIResponse(String status, String prompt) {}

record Flashcard(String concept, String definition) {}

@Service
public class flashcardService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public flashcardService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://hackathon-1pvb.onrender.com")
                .defaultHeader("X-API-Key", "sk_79570a7ab6c9eee78652a85bff9b2fdde8d64066")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String getCleanAIResponse(String message) throws Exception {
        // We add a "System Instruction" inside the prompt to force JSON output
        String instruction = "Return a JSON array of 3 flashcards. Each object must have 'concept' and 'definition'. Topic: ";
        String jsonBody = "{\"context\": \"" + instruction + message + "\"}";

        String rawResponse = restClient.post()
                .uri("/api/ai-model/v2/chat")
                .body(jsonBody)
                .retrieve()
                .body(String.class);

        // Parse the response and extract flashcards
        return extractFlashcardsFromResponse(rawResponse);
    }

    private String extractFlashcardsFromResponse(String rawResponse) throws Exception {
        try {
            // Parse the API response
            AIResponse aiResponse = objectMapper.readValue(rawResponse, AIResponse.class);
            String promptText = aiResponse.prompt();
            
            // Extract JSON array from prompt text using regex
            Pattern pattern = Pattern.compile("\\[(.*?)\\]", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(promptText);
            
            if (matcher.find()) {
                String jsonArrayStr = "[" + matcher.group(1) + "]";
                
                // Parse the JSON array
                JsonNode arrayNode = objectMapper.readTree(jsonArrayStr);
                List<Map<String, String>> flashcards = new ArrayList<>();
                
                for (JsonNode node : arrayNode) {
                    Map<String, String> flashcard = new HashMap<>();
                    flashcard.put("concept", node.get("concept").asText());
                    flashcard.put("definition", node.get("definition").asText());
                    flashcards.add(flashcard);
                }
                
                // Return the flashcards as JSON
                return objectMapper.writeValueAsString(flashcards);
            }
            
            // Fallback: return empty array
            return objectMapper.writeValueAsString(Collections.emptyList());
            
        } catch (Exception e) {
            System.err.println("Error extracting flashcards: " + e.getMessage());
            return objectMapper.writeValueAsString(Collections.emptyList());
        }
    }
}