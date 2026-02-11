package com.portfolio.chatbot.chat.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "Chatbot response payload")
public class ChatResponse {

    @Schema(description = "Assistant answer to the user question", example = "I'm doing well, thanks for asking!")
    private String answer;

    public ChatResponse(String answer) {
        this.answer = answer;
    }

    public static ChatResponse of(String answer) {
        return new ChatResponse(answer);
    }
}
