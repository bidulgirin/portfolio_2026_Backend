package com.portfolio.chatbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.util.StringUtils;

@Configuration
public class OpenAIConfig {

    @Bean
    public RestClient openAiRestClient(
            @Value("${openai.api-key:}") String apiKey,
            @Value("${openai.api-url}") String apiUrl
    ) {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        if (StringUtils.hasText(apiKey)) {
            builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
        }

        // Build a dedicated RestClient instead of relying on an auto-configured RestClient.Builder.
        return builder.build();
    }
}
