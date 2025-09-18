package com.crediya.solicitudes.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Component
public class RemoteJwtValidator {

    private final WebClient webClient;

    public RemoteJwtValidator(@Value("${auth.service.url:http://localhost:8080}") String authServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(authServiceUrl)
                .build();
    }

@SuppressWarnings("unchecked")
    public Mono<Map<String, Object>> validateToken(String token) {
        return webClient.post()
                .uri("/api/v1/auth/validate")
                .bodyValue(Map.of("token", token))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (Map<String, Object>) response)
                .doOnNext(response -> log.info("Token validation response: {}", response))
                .onErrorResume(error -> {
                    log.error("Error validating token remotely: {}", error.getMessage());
                    return Mono.error(new RuntimeException("Token validation failed"));
                });
    }
}