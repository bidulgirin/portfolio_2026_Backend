package com.portfolio.chatbot.chat.domain;

import java.time.Instant;

public class ChatMessage {
    private final String role;
    private final String content;
    private final Instant createdAt;

    public ChatMessage(String role, String content, Instant createdAt) {
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
