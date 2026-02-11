package com.portfolio.chatbot.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "Chat request payload")
public class ChatRequest {

    @Schema(description = "User question to send to the model", example = "Hello, how are you?")
    @NotBlank(message = "message must not be blank")
    private String message;

    public ChatRequest(String message) {
        this.message = message;
    }
}
