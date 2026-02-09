package com.portfolio.chatbot.rag.domain;

public class DocumentChunk {
    private final String id;
    private final String content;

    public DocumentChunk(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
