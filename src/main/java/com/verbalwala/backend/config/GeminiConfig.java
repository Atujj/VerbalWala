package com.verbalwala.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GeminiConfig {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Bean
    public RestClient geminiRestClient() {

        return RestClient.builder()
                .baseUrl(
                        "https://generativelanguage.googleapis.com/v1beta/models/"
                )
                .defaultHeader(
                        "x-goog-api-key",
                        apiKey
                )
                .build();
    }

}