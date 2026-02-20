package com.portfolio.chatbot.chat.audit;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatAuditEntry {
    private final String type;
    private final String message;
    private final String clientIp;
    private final Instant createdAt;

    public ChatAuditEntry(String type, String message, String clientIp, Instant createdAt) {
        this.type = type;
        this.message = message;
        this.clientIp = clientIp;
        this.createdAt = createdAt;
    }

    public static ChatAuditEntry user(String message, String clientIp) {
        return new ChatAuditEntry("user", message, clientIp, Instant.now());
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getClientIp() {
        return clientIp;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
