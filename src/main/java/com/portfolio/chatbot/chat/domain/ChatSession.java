package com.portfolio.chatbot.chat.domain;

public class ChatSession {
    private final String sessionId;

    public ChatSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
