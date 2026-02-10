# 파일구조


```text
 src/main/java/com/portfolio/chatbot
├─ ChatbotApplication.java
├─ config/
│  ├─ WebConfig.java
│  ├─ SecurityConfig.java            
│  ├─ OpenAiConfig.java              (LLM 연동 설정)
│  └─ JacksonConfig.java             
├─ common/
│  ├─ exception/
│  │  ├─ GlobalExceptionHandler.java
│  │  └─ ErrorCode.java
│  ├─ response/
│  │  ├─ ApiResponse.java
│  │  └─ ErrorResponse.java
│  └─ util/
│     └─ TimeUtil.java
├─ chat/
│  ├─ controller/
│  │  └─ ChatController.java
│  ├─ dto/
│  │  ├─ ChatRequest.java
│  │  └─ ChatResponse.java
│  ├─ service/
│  │  ├─ ChatService.java
│  │  └─ PromptBuilder.java          (프롬프트/시스템메시지 구성)
│  ├─ client/
│  │  └─ LlmClient.java              (OpenAI/Claude 등 외부 API 호출)
│  └─ domain/
│     ├─ ChatMessage.java
│     └─ ChatSession.java            (선택: 세션 기반)
├─ memory/                           (선택: 대화 저장/검색)
│  ├─ repository/
│  │  ├─ ChatMessageRepository.java
│  │  └─ ChatSessionRepository.java
│  └─ service/
│     └─ MemoryService.java
└─ rag/                               (문서 기반 Q&A)
   ├─ service/
   │  ├─ Retriever.java              (유사도 검색)
   │  └─ RAGService.java             (검색+응답 생성)
   └─ domain/
      └─ DocumentChunk.java

