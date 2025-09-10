package com.crediya.solicitudes.api;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class JwtDebugHandler {

    public Mono<ServerResponse> disableJwtValidation(ServerRequest request) {
        // Temporarily disable JWT validation for testing
        System.setProperty("jwt.validation.disabled", "true");
        
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("message", "JWT validation disabled for testing"));
    }
}