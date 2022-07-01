package br.com.cardoso.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SleuthService {

    private final RestTemplate restTemplate;

    public SleuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String hello() {
        String path = "http://localhost:8080/world";
        ResponseEntity<String> response = restTemplate.getForEntity(path, String.class);
        return response.getBody();
    }
}
