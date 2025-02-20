package com.sparta.twotwo.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.twotwo.ai.config.GeminiConfig;
import com.sparta.twotwo.ai.entity.AIRequestLog;
import com.sparta.twotwo.ai.repository.AIRequestLogRepository;
import com.sparta.twotwo.enums.AIRequestStatus;
import com.sparta.twotwo.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIService {
    private final GeminiConfig geminiConfig;
    private final AIRequestLogRepository aiRequestLogRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

    @Transactional
    public AIRequestLog generateProductDescription(Product product, String prompt) {
        AIRequestLog aiRequestLog = new AIRequestLog();
        aiRequestLog.setProduct(product);
        aiRequestLog.setRequestText(prompt);
        aiRequestLog.setStatus(AIRequestStatus.PENDING);
        aiRequestLog = aiRequestLogRepository.save(aiRequestLog); // 로그 먼저 저장

        try {
            String response = callAIAPI(prompt);

            if (response == null || response.isEmpty()) {
                aiRequestLog.setStatus(AIRequestStatus.FAILED);
                aiRequestLog.setResponseText("AI 응답이 비어 있습니다.");
            } else {
                aiRequestLog.setResponseText(response);
                aiRequestLog.setStatus(AIRequestStatus.SUCCESS);
            }
        } catch (Exception e) {
            aiRequestLog.setStatus(AIRequestStatus.FAILED);
            aiRequestLog.setResponseText("AI API 요청 실패: " + e.getMessage());
            log.error("AI API 요청 실패", e);
        }

        return aiRequestLogRepository.save(aiRequestLog);
    }

    private String callAIAPI(String prompt) {
        String API_KEY = geminiConfig.getKey();
        if (API_KEY == null || API_KEY.isBlank()) {
            throw new IllegalStateException("API Key가 설정되지 않았습니다.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        // 요청 본문을 Map으로 생성 후 JSON 변환
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt + " 답변을 최대한 간결하게 50자 이하로")
                        ))
                )
        );

        try {
            String jsonRequestBody = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            return parseAIResponse(response.getBody());
        } catch (Exception e) {
            log.error("AI API 요청 실패", e);
            return "AI 응답을 처리하는 중 오류 발생";
        }
    }

    private String parseAIResponse(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);

            if (root.hasNonNull("candidates") && root.get("candidates").isArray() && root.get("candidates").size() > 0) {
                JsonNode candidate = root.get("candidates").get(0);
                if (candidate.hasNonNull("content") && candidate.get("content").hasNonNull("parts") &&
                        candidate.get("content").get("parts").isArray() && candidate.get("content").get("parts").size() > 0) {
                    return candidate.get("content").get("parts").get(0).path("text").asText("AI 응답 없음");
                }
            }

            return "AI 응답이 올바르지 않습니다.";
        } catch (Exception e) {
            log.error("AI 응답 파싱 오류", e);
            return "AI 응답을 처리하는 중 오류 발생";
        }
    }
}