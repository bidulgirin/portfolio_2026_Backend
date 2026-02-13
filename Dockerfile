FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./
RUN chmod +x gradlew

# 의존성 캐시를 위해 소스 복사 전에 한 번 실행
RUN ./gradlew dependencies --no-daemon || true

COPY src src
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0"
ENTRYPOINT ["java","-jar","/app/app.jar"]
