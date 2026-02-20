package com.portfolio.chatbot.chat.controller;

import com.portfolio.chatbot.chat.dto.ChatRequest;
import com.portfolio.chatbot.chat.dto.ChatResponse;
import com.portfolio.chatbot.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@Tag(name = "Chat", description = "Chat completion endpoint for OpenAI powered answers")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    @Operation(summary = "Ask the chatbot", description = "Sends a user message and returns the assistant answer.")
    @ApiResponse(responseCode = "200", description = "Chatbot answer", content = @Content(schema = @Schema(implementation = ChatResponse.class)))
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest chatRequest, HttpServletRequest request) {
        String clientIp = resolveClientIp(request);
        String answer = chatService.getAnswer(chatRequest.getMessage(), clientIp);

        return ResponseEntity.ok(ChatResponse.of(answer));
    }

    private String resolveClientIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(forwardedFor)) {
            String first = forwardedFor.split(",")[0].trim();
            if (StringUtils.hasText(first)) {
                return first;
            }
        }
        String realIp = request.getHeader("X-Real-IP");
        if (StringUtils.hasText(realIp)) {
            return realIp.trim();
        }
        return request.getRemoteAddr();
    }
}
