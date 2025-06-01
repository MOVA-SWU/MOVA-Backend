package com.example.mova.Ai;

import com.example.mova.dto.AiTaskDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AiClient {
    private final RestTemplate restTemplate;

    public AiClient() {
        this.restTemplate = new RestTemplate();
    }

    public AiTaskDto.ResponseFromAi sendToAi(AiTaskDto.RequestToAi request){
       String api = "AIzaSyBVBoQRbb1nWOYlL9srcFH4swLInwOToRU";
       return restTemplate.postForObject(api, request, AiTaskDto.ResponseFromAi.class);
    }
}
