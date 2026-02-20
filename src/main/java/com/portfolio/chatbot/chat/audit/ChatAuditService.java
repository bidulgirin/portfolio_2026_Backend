package com.portfolio.chatbot.chat.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class ChatAuditService {
    private static final Logger log = LoggerFactory.getLogger(ChatAuditService.class);

    private final ObjectMapper objectMapper;
    private final boolean enabled;
    private final String auditPath;
    private final boolean maskEnabled;
    private final String maskReplacement;

    public ChatAuditService(
            ObjectMapper objectMapper,
            @Value("${chat.audit.enabled:false}") boolean enabled,
            @Value("${chat.audit.path:./logs/chat-audit.jsonl}") String auditPath,
            @Value("${chat.audit.mask-enabled:true}") boolean maskEnabled,
            @Value("${chat.audit.mask-replacement:[REDACTED]}") String maskReplacement
    ) {
        this.objectMapper = objectMapper;
        this.enabled = enabled;
        this.auditPath = auditPath;
        this.maskEnabled = maskEnabled;
        this.maskReplacement = maskReplacement;
    }

    public void recordUserMessage(String message, String clientIp) {
        if (!enabled || !StringUtils.hasText(message)) {
            return;
        }
        String toStore = maskEnabled ? PiiMasker.mask(message, maskReplacement) : message;
        ChatAuditEntry entry = ChatAuditEntry.user(toStore, clientIp);
        write(entry);
    }

    private synchronized void write(ChatAuditEntry entry) {
        try {
            Path path = Paths.get(auditPath);
            Path parent = path.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            String json = objectMapper.writeValueAsString(entry);
            Files.writeString(
                    path,
                    json + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND
            );
        } catch (Exception e) {
            log.warn("Failed to write chat audit entry", e);
        }
    }
}
