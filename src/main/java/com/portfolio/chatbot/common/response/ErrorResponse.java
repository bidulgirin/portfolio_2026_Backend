package com.portfolio.chatbot.common.response;

import com.portfolio.chatbot.common.util.TimeUtil;

public class ErrorResponse {
    private final String code;
    private final String message;
    private final String timestamp;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = TimeUtil.nowIso();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
