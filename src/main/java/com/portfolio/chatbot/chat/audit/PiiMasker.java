package com.portfolio.chatbot.chat.audit;

import java.util.regex.Pattern;

public final class PiiMasker {
    private static final Pattern EMAIL =
            Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[A-Za-z]{2,}");
    private static final Pattern PHONE =
            Pattern.compile("(?<!\\d)(\\+?\\d{1,3}[-.\\s]?)?(\\d{2,4}[-.\\s]?){2,4}\\d{2,4}(?!\\d)");
    private static final Pattern KOREAN_RRN =
            Pattern.compile("\\b\\d{6}[- ]?[1-4]\\d{6}\\b");
    private static final Pattern CREDIT_CARD =
            Pattern.compile("\\b(?:\\d[ -]*?){13,19}\\b");

    private PiiMasker() {
    }

    public static String mask(String input, String replacement) {
        if (input == null || input.isBlank()) {
            return input;
        }
        String safeReplacement = (replacement == null || replacement.isBlank()) ? "[REDACTED]" : replacement;
        String masked = EMAIL.matcher(input).replaceAll(safeReplacement);
        masked = PHONE.matcher(masked).replaceAll(safeReplacement);
        masked = KOREAN_RRN.matcher(masked).replaceAll(safeReplacement);
        masked = CREDIT_CARD.matcher(masked).replaceAll(safeReplacement);
        return masked;
    }
}
