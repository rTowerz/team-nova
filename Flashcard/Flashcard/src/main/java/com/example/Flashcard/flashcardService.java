package com.example.Flashcard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;

// This record helps Jackson map the JSON data
record AIResponse(String status, String prompt) {}

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

        // Return the raw JSON string directly to the controller
        return rawResponse;
    }
}