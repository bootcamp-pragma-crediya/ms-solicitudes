package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.user.User;
import com.crediya.solicitudes.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "user.service.enabled", havingValue = "true", matchIfMissing = false)
public class UserRepositoryAdapter implements UserRepository {

    private final WebClient webClient;
    
    @Value("${auth.service.url:http://ms-autenticacion:8080}")
    private String authServiceUrl;

    @Override
    public Mono<User> findById(String userId) {
        String url = authServiceUrl + "/api/v1/usuarios/" + userId;
        log.info("[UserService] Fetching user data from URL: {} for userId: {}", url, userId);
        
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .map(userResponse -> User.builder()
                        .id(userResponse.id())
                        .name(userResponse.name())
                        .lastName(userResponse.last_name())
                        .email(userResponse.email())
                        .baseSalary(userResponse.base_salary())
                        .build())
                .doOnSuccess(user -> log.info("[UserService] Retrieved user data: name={}, salary={} for userId: {}", 
                    user.getName(), user.getBaseSalary(), userId))
                .doOnError(error -> log.error("[UserService] Error fetching user data for userId: {} - {}", userId, error.getMessage(), error));
    }
    
    private record UserResponse(
        String id,
        String name,
        String last_name,
        String email,
        BigDecimal base_salary
    ) {}
}