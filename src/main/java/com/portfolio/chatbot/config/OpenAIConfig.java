package com.portfolio.chatbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class OpenAIConfig {

    @Bean
    public RestClient openAiRestClient(
            @Value("${openai.api-key}") String apiKey,
            @Value("${openai.api-url}") String apiUrl
    ) {
        // Build a dedicated RestClient instead of relying on an auto-configured RestClient.Builder.
        return RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
