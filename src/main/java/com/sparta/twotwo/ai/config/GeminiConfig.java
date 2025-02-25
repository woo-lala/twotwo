package com.sparta.twotwo.ai.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gemini.api")
@Slf4j
public class GeminiConfig {
    private String key;

    @PostConstruct
    public void init() {
        log.info("Gemini API Key: {}", key != null ? "API 키 로드 성공" : "API 키가 설정되지 않음");
    }
}