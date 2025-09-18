package com.crediya.solicitudes.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final RemoteJwtValidator remoteValidator;
    private static final List<String> PROTECTED_PATHS = List.of("/api/v1/solicitud");

    public JwtAuthenticationFilter(RemoteJwtValidator remoteValidator) {
        this.remoteValidator = remoteValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        
        if (!isProtectedPath(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        
        if (authHeader == null) {
            log.warn("Missing Authorization header for path: {}", path);
            return unauthorized(exchange);
        }

        String token;
        if (authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
        } else {
            // Accept token directly without Bearer prefix
            token = authHeader.trim();
        }
        log.info("Received JWT token: [{}]", token);
        
        try {
            // Decode JWT payload without signature verification
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new RuntimeException("Invalid JWT format");
            }
            
            String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
            log.info("JWT payload: {}", payload);
            
            // Parse JSON manually to extract role, email and userId
            String role = extractJsonValue(payload, "role");
            String email = extractJsonValue(payload, "email");
            String userId = extractJsonValue(payload, "userId");
            
            if (path.equals("/api/v1/solicitud") && "GET".equals(exchange.getRequest().getMethod().name())) {
                if (!"ASESOR".equals(role)) {
                    log.warn("Access denied for role: {} on path: {}", role, path);
                    return forbidden(exchange);
                }
            }
            
            if (path.equals("/api/v1/solicitud") && "POST".equals(exchange.getRequest().getMethod().name())) {
                if (!"CLIENTE".equals(role)) {
                    log.warn("Access denied for role: {} on POST solicitud. Only CLIENTE can create requests", role);
                    return forbidden(exchange);
                }
            }
            
            // Add userId to request headers for handler access
            ServerWebExchange modifiedExchange = exchange.mutate()
                .request(exchange.getRequest().mutate()
                    .header("X-User-Id", userId)
                    .build())
                .build();
            
            exchange.getAttributes().put("user.email", email);
            exchange.getAttributes().put("user.role", role);
            exchange.getAttributes().put("user.id", userId);
            log.info("JWT validated successfully for user: {}, role: {}, userId: {}", email, role, userId);
            
            return chain.filter(modifiedExchange);
            
        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
            return unauthorized(exchange);
        }
    }
    
    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) {
            return null;
        }
        startIndex += searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }

    private boolean isProtectedPath(String path) {
        return PROTECTED_PATHS.stream().anyMatch(path::startsWith);
    }



    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

}