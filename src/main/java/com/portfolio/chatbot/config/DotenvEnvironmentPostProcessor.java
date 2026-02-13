package com.portfolio.chatbot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final String DOTENV_FILENAME = ".env";
    private static final String PROPERTY_SOURCE_NAME = "dotenv";
    private static final Logger log = LoggerFactory.getLogger(DotenvEnvironmentPostProcessor.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, org.springframework.boot.SpringApplication application) {
        Path dotenvPath = resolveDotenvPath(environment);
        if (dotenvPath == null || !Files.exists(dotenvPath)) {
            return;
        }

        Map<String, Object> values = new LinkedHashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(dotenvPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }
                if (trimmed.startsWith("export ")) {
                    trimmed = trimmed.substring(7).trim();
                }
                int idx = trimmed.indexOf('=');
                if (idx <= 0) {
                    continue;
                }
                String key = trimmed.substring(0, idx).trim();
                key = stripBom(key);
                String value = trimmed.substring(idx + 1).trim();
                value = stripQuotes(value);
                if (!key.isEmpty()) {
                    values.put(key, value);
                }
            }
        } catch (IOException ignored) {
            return;
        }

        if (!values.isEmpty() && environment.getPropertySources().get(PROPERTY_SOURCE_NAME) == null) {
            environment.getPropertySources().addLast(new MapPropertySource(PROPERTY_SOURCE_NAME, values));
            log.info("Loaded .env from {} ({} entries)", dotenvPath.toAbsolutePath(), values.size());
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private static String stripQuotes(String value) {
        if (value.length() >= 2) {
            char first = value.charAt(0);
            char last = value.charAt(value.length() - 1);
            if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
                return value.substring(1, value.length() - 1);
            }
        }
        return value;
    }

    private static String stripBom(String value) {
        if (!value.isEmpty() && value.charAt(0) == '\uFEFF') {
            return value.substring(1);
        }
        return value;
    }

    private static Path resolveDotenvPath(ConfigurableEnvironment environment) {
        String explicit = environment.getProperty("app.dotenv.path");
        if (!StringUtils.hasText(explicit)) {
            explicit = System.getenv("DOTENV_PATH");
        }
        if (StringUtils.hasText(explicit)) {
            Path configured = Paths.get(explicit);
            if (Files.exists(configured)) {
                return configured;
            }
        }

        Path cwd = Paths.get(System.getProperty("user.dir"), DOTENV_FILENAME);
        if (Files.exists(cwd)) {
            return cwd;
        }

        Path jarDir = getJarDir();
        if (jarDir != null) {
            Path sibling = jarDir.resolve(DOTENV_FILENAME);
            if (Files.exists(sibling)) {
                return sibling;
            }
            Path parent = jarDir.getParent();
            if (parent != null) {
                Path parentDotenv = parent.resolve(DOTENV_FILENAME);
                if (Files.exists(parentDotenv)) {
                    return parentDotenv;
                }
            }
        }
        return null;
    }

    private static Path getJarDir() {
        try {
            URL location = DotenvEnvironmentPostProcessor.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation();
            if (location == null) {
                return null;
            }
            Path path = Paths.get(location.toURI());
            if (Files.isRegularFile(path)) {
                return path.getParent();
            }
            if (Files.isDirectory(path)) {
                return path;
            }
        } catch (URISyntaxException ignored) {
            return null;
        }
        return null;
    }
}
