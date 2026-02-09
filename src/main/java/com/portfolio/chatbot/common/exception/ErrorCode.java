package com.portfolio.chatbot.common.exception;

public enum ErrorCode {
    BAD_REQUEST("BAD_REQUEST", "Bad request"),
    INTERNAL_ERROR("INTERNAL_ERROR", "Unexpected error");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
