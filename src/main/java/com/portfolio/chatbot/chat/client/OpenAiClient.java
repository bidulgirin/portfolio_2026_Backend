package com.portfolio.chatbot.chat.client;

import com.portfolio.chatbot.chat.domain.ChatMessage;
import com.portfolio.chatbot.chat.dto.OpenAiRequest;
import com.portfolio.chatbot.chat.dto.OpenAiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class OpenAiClient {

    private static final String ROLE_SYSTEM = "system";
    private static final String ROLE_USER = "user";

    private static final Logger log = LoggerFactory.getLogger(OpenAiClient.class);

    private final RestClient openAiRestClient;
    private final String model;
    private final String systemPrompt;
    private final String apiKey;

    public OpenAiClient(
            @Qualifier("openAiRestClient") RestClient openAiRestClient,
            @Value("${openai.api-key:}") String apiKey,
            @Value("${openai.model}") String model,
            @Value("${openai.system-prompt:}") String systemPrompt
    ) {
        this.openAiRestClient = openAiRestClient;
        this.apiKey = apiKey;
        this.model = model;
        this.systemPrompt = systemPrompt;
    }

    public OpenAiResponse getChatCompletion(String question) {
        if (!StringUtils.hasText(apiKey)) {
            throw new IllegalStateException("OPENAI_API_KEY is not configured. Set it in the environment or .env file.");
        }
        if (!StringUtils.hasText(question)) {
            throw new IllegalArgumentException("question must not be blank");
        }

        int questionLength = question.length();
        boolean hasSystemPrompt = StringUtils.hasText(systemPrompt);
        log.info("OpenAiClient.getChatCompletion called. model={}, questionLength={}, systemPrompt={}",
                model, questionLength, hasSystemPrompt);

        List<ChatMessage> messages = StringUtils.hasText(systemPrompt)
                ? List.of(
                        new ChatMessage(ROLE_SYSTEM, systemPrompt, null),
                        new ChatMessage(ROLE_USER, question, null)
                )
                : List.of(new ChatMessage(ROLE_USER, question, null));

        OpenAiRequest request = new OpenAiRequest(model, messages, true);

        try {
            OpenAiResponse response = openAiRestClient.post()
                    .body(request)
                    .retrieve()
                    .body(OpenAiResponse.class);
            int choiceCount = (response != null && response.getChoices() != null)
                    ? response.getChoices().size()
                    : 0;
            log.info("OpenAiClient.getChatCompletion completed. choices={}", choiceCount);
            return response;
        } catch (RestClientException e) {
            throw new IllegalStateException("Failed to call OpenAI chat completions", e);
        }
    }
}
