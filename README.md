# Chatbot API

## Structure

```text
src/main/java/com/portfolio/chatbot
├─ ChatbotApplication.java
├─ config/
│  ├─ OpenAIConfig.java
│  ├─ SwaggerConfig.java
│  └─ WebConfig.java
├─ common/
│  ├─ exception/
│  │  ├─ GlobalExceptionHandler.java
│  │  └─ ErrorCode.java
│  ├─ response/
│  │  ├─ ApiResponse.java
│  │  └─ ErrorResponse.java
│  └─ util/
│     └─ TimeUtil.java
└─ chat/
   ├─ controller/
   │  └─ ChatController.java
   ├─ dto/
   │  ├─ ChatRequest.java
   │  └─ ChatResponse.java
   ├─ service/
   │  └─ ChatService.java
   ├─ client/
   │  └─ OpenAiClient.java
   └─ domain/
      └─ ChatMessage.java
```

## Environment

Required:
- `OPENAI_API_KEY`

Optional:
- `OPENAI_API_URL` (default: `https://api.openai.com/v1/chat/completions`)
- `OPENAI_MODEL` (default: `gpt-3.5-turbo`)
- `OPENAI_SYSTEM_PROMPT`
- `APP_CORS_ALLOWED_ORIGINS` (default: `http://localhost:8081,http://127.0.0.1:8081`)

`.env` in the working directory is automatically loaded at startup.
You can also set `app.dotenv.path` or `DOTENV_PATH` to point to a custom `.env` location.

## Run (local)

```bash
./gradlew bootRun
```

## Run (Docker)

```bash
docker compose up --build
```

## API

`POST /api/chat`

Request body:

```json
{
  "message": "Hello"
}
```

Response:

```json
{
  "message": "Hi! How can I help you?"
}
```
