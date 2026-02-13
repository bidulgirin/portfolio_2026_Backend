package com.portfolio.chatbot.chat.service;

import com.portfolio.chatbot.chat.client.OpenAiClient;
import com.portfolio.chatbot.chat.dto.OpenAiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private final OpenAiClient openAiClient;

    /**
     * 클라이언트 질문을 받아 OpenAI 응답 텍스트 반환
     */
    public String getAnswer(String question) {
        int questionLength = (question != null) ? question.length() : 0;
        log.info("ChatService.getAnswer called. questionLength={}", questionLength);
        OpenAiResponse res = openAiClient.getChatCompletion(question);

        String content =
                (res != null && res.getChoices() != null && !res.getChoices().isEmpty()
                        && res.getChoices().get(0) != null
                        && res.getChoices().get(0).getMessage() != null)
                        ? res.getChoices().get(0).getMessage().getContent()
                        : null;

        if (content == null || content.isBlank()) {
            throw new IllegalStateException("OpenAI 응답 텍스트를 추출할 수 없습니다. response=" + res);
        }
        log.info("ChatService.getAnswer completed. answerLength={}", content.length());
        return content;
    }
}
