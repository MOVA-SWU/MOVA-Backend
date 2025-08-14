package com.example.mova.Ai;

import com.example.mova.dto.AiTaskDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AiClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiClient() {
        this.restTemplate = new RestTemplate();
    }

    public AiTaskDto.ResponseFromAi sendToAi(AiTaskDto.RequestToAi request){
       String apiKey = "hjgjhgjhgkjgkjg";
       String url = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=" + apiKey;
        // 1. 프롬프트 + 제목 조합
        String prompt = buildPrompt(request.getTitle());

        // 2. 요청 JSON 구성
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
        """.formatted(prompt.replace("\"", "\\\"")); // 큰따옴표 이스케이프

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        // 3. 요청 전송
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // 4. 응답의 text 필드에서 JSON 파싱
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
        평화: https://github.com/user-attachments/assets/3c31966f-1ff7-4de0-929d-fe869c83be0f
        인권: https://github.com/user-attachments/assets/e45f20e9-af0e-4884-9eec-424a71dd433b
        빈곤: https://github.com/user-attachments/assets/1df3c0de-8d2e-4872-a27b-44f770602918
        교육: https://github.com/user-attachments/assets/b7db40e1-94da-4309-ad1e-dc9ae2d9ab12
        환경: https://github.com/user-attachments/assets/56a8c740-67ea-4365-81d5-c2ed5d3bb6a1
        과학: https://github.com/user-attachments/assets/632def32-0fcc-4314-99f9-8671cfc239f0
        건강: https://github.com/user-attachments/assets/769f537f-bede-462d-9710-9b2b5ac66b0e
        공동체: https://github.com/user-attachments/assets/dce27d74-7ed1-4fed-8e1c-2303d4794ec4
        윤리: https://github.com/user-attachments/assets/61fe198b-cdb3-4c0c-87c8-17f5be58dbc5

        아래 JSON 예시처럼 반환해주세요:
        {
          "movie": "영화 제목",
          "mission": "미션 내용",
          "effect": "미션의 효과",
          "point_message": "미션을 하면 100포인트를 드려요!",
          "point": 100,
          "theme": "환경",
          "image_url": "https://github.com/user-attachments/assets/56a8c740-67ea-4365-81d5-c2ed5d3bb6a1"
        }
        """.formatted(title);
    }


}
