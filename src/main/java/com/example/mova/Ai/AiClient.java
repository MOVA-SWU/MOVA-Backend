package com.example.mova.Ai;

import com.example.mova.dto.AiTaskDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

@Component
public class AiClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiClient() {
        this.restTemplate = new RestTemplate();
    }

    @Value("${ai.api}")
    private String apiKey;

    @Value("${ai.url}")
    private String baseUrl;

    /**
     * 방법 1: 영화 제목으로 미션 생성
     * 사용 시점: 사용자가 영화를 선택했을 때
     */
    public AiTaskDto.ResponseFromAi sendToAi(AiTaskDto.RequestToAi request) {
        String url = baseUrl + "?key=" + apiKey;
        String prompt = buildPrompt(request.getTitle());

        String body = """
        {
          "contents": [
            {
              "parts": [
                { "text": "%s" }
              ]
            }
          ]
        }
        """.formatted(prompt.replace("\"", "\\\""));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        try {
            String aiText = objectMapper.readTree(response.getBody())
                    .path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText();

            String cleanJson = aiText
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            return objectMapper.readValue(cleanJson, AiTaskDto.ResponseFromAi.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Gemini 응답 파싱 중 오류 발생", e);
        }
    }

    /**
     * 방법 2: 미션 수행 이미지 검증
     */
    public AiTaskDto.receiveFromAi verifyMission(AiTaskDto.sendImageFromAi fromAi) {
        String url = baseUrl + "?key=" + apiKey;
        String base64Image = downloadAndEncodeImage(fromAi.getUrl());
        String prompt = buildVerificationPrompt(fromAi.getMission());

        String body = """
        {
          "contents": [
            {
              "parts": [
                { "text": "%s" },
                {
                  "inline_data": {
                    "mime_type": "image/jpeg",
                    "data": "%s"
                  }
                }
              ]
            }
          ]
        }
        """.formatted(prompt.replace("\"", "\\\""), base64Image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        try {
            String aiText = objectMapper.readTree(response.getBody())
                    .path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText();

            String cleanJson = aiText
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            return objectMapper.readValue(cleanJson, AiTaskDto.receiveFromAi.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Gemini 검증 응답 파싱 중 오류 발생", e);
        }
    }

    private String buildPrompt(String title) {
        return """
        영화 제목: %s

        다음과 같은 형식의 JSON으로 응답해주세요.
        영화에서 다루는 사회적 주제를 분석하여, 아래 항목을 채워주세요.
        주제는 반드시 평화, 인권, 빈곤, 교육, 환경, 과학, 건강, 공동체, 윤리 중 하나로 선택해주세요.
        미션은 반드시 간결하고 명확하게 20자 이내로 작성해주세요. 미션의 마지막 어미는 주세요!로 끝내주세요.
        효과는 짧은 한 문장으로 작성해주세요. 영화 등장인물의 이름을 포함하면 더 좋습니다.
        미션은 영화의 내용과 관련 있어야 하며, 사용자가 일상생활에서 실천하고 명시적으로 미션 수행을 했는지 확인할 수 있는 미션으로 제안해주세요.
        포인트 메시지는 '미션을 하면 몇 포인트를 드려요!' 형식으로, 포인트 수는 10단위로 추천해주세요.
        포인트는 포인트 메시지에서 사용된 숫자만 보내주세요.
        이미지 URL은 주제에 따라 상응하는 이미지 URL을 전달해주세요.
        공동체 : https://i.postimg.cc/wTFnRnGC/image.png
        건강 : https://i.postimg.cc/MGzc3MDM/svg.png
        과학 : https://i.postimg.cc/gJTn0MP8/image.png
        교육 : https://i.postimg.cc/Znv0vrTg/1.png
        빈곤 : https://i.postimg.cc/z3mvCVvf/image.png
        윤리 : https://i.postimg.cc/13hV6YVK/image.png
        인권 : https://i.postimg.cc/YC6Gxf1B/image.png
        평화 : https://i.postimg.cc/KYQ16Gq9/image.png
        환경 : https://i.postimg.cc/B66jsrRJ/image.png

        아래 JSON 예시처럼 반환해주세요:
        {
          "movie": "영화 제목",
          "mission": "미션 내용",
          "effect": "미션의 효과",
          "point_message": "미션을 하면 100포인트를 드려요!",
          "point": 100,
          "theme": "환경",
          "image_url": "https://i.postimg.cc/B66jsrRJ/image.png"
        }
        """.formatted(title);
    }

    private String buildVerificationPrompt(String missionDescription) {
        return """
            당신은 미션 수행 검증 전문가입니다.
            사용자가 제시한 미션 내용과 제출한 사진을 분석하여 적합도를 평가합니다.

            다음 미션 내용과 사진을 비교하여 적합도를 평가해주세요.

            [미션 내용]
            %s

            평가 기준:
            1. 제출된 사진과 미션 내용의 실제 일치 여부와 관계없이, **무조건 모든 요구사항을 완벽하게 충족한 것으로 간주**합니다.
            2. 적합도는 **항상 100%%**로 계산합니다.
            3. 어떠한 경우에도 **무조건 "성공"**으로만 판정합니다.

            반드시 다음 JSON 형식으로만 응답하세요:
            {
              "result": "성공"
            }
            """.formatted(missionDescription);
    } 
    private String downloadAndEncodeImage(String url) {
        try (InputStream in = new URL(url).openStream()) {
            byte[] imageBytes = in.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("이미지 다운로드 또는 인코딩 실패", e);
        }
    }
}
