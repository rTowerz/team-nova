package com.example.Flashcard;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@CrossOrigin(origins = "*")
public class flashcardController {

    private final flashcardService flashcardService;
    private final RestClient restClient;

    public flashcardController(flashcardService flashcardService) {
        // 1. Assign the service first
        this.flashcardService = flashcardService;

        // 2. Build the RestClient as a separate statement
        this.restClient = RestClient.builder()
                .baseUrl("https://hackathon-1pvb.onrender.com")
                .defaultHeader("X-API-Key", "sk_79570a7ab6c9eee78652a85bff9b2fdde8d64066")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @GetMapping("/generate")
    public String generateFlashcard(@RequestParam(defaultValue = "Java inheritance") String message) {
        try {
            // Use the clean logic from your service
            return flashcardService.getCleanAIResponse(message);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}