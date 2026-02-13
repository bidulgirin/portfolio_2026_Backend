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

The application reads environment variables at runtime (no `.env` auto-loading).

## Run (local)

```bash
./gradlew bootRun
```

## Run (Docker)

```bash
docker compose up --build
```

## Deploy (EC2 Docker)

```bash
# run with environment variables
docker run -d \
  --name chatbot-backend \
  --restart unless-stopped \
  -p 8080:8080 \
  -e OPENAI_API_KEY=... \
  -e OPENAI_API_URL=https://api.openai.com/v1/chat/completions \
  -e OPENAI_MODEL=gpt-3.5-turbo \
  -e OPENAI_SYSTEM_PROMPT=... \
  -e APP_CORS_ALLOWED_ORIGINS=https://staging.d2y4knmwkrsavb.amplifyapp.com \
  <DOCKERHUB_ID>/chatbot-backend:latest
```

## GitHub Actions Deploy (Secrets)

Required repository secrets:
- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`
- `EC2_HOST`
- `EC2_USER`
- `EC2_SSH_KEY`
- `OPENAI_API_KEY`

Optional secrets:
- `OPENAI_API_URL`
- `OPENAI_MODEL`
- `OPENAI_SYSTEM_PROMPT`
- `APP_CORS_ALLOWED_ORIGINS`

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
