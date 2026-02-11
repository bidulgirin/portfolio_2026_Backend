package com.portfolio.chatbot.chat.dto;

import com.portfolio.chatbot.chat.domain.ChatMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenAiRequest {

    // 사용할 모델명
    private String model;

    // 메세지 리스트
    private List<ChatMessage> messages;

    public OpenAiRequest(String model, List<ChatMessage> messages) {
        this.model = model;
        this.messages = messages;
    }
}