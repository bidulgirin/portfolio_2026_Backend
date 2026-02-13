package com.portfolio.chatbot.chat.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessage {
    private final String role;
    private final String content;
    private final Instant createdAt;

    @JsonCreator
    public ChatMessage(
            @JsonProperty("role") String role,
            @JsonProperty("content") String content,
            @JsonProperty("createdAt") Instant createdAt
    ) {
        this.role = role;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
