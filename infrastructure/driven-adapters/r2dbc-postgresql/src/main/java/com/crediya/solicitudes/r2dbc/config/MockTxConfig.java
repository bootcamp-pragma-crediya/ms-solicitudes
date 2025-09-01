package com.crediya.solicitudes.r2dbc.config;

import com.crediya.solicitudes.model.common.ReactiveTransaction;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
@ConditionalOnProperty(name = "app.mock-mode", havingValue = "true", matchIfMissing = true)
public class MockTxConfig {
    
    @Bean
    ReactiveTransaction reactiveTransaction() {
        return new ReactiveTransaction() {
            @Override
            public <T> Mono<T> transactional(Mono<T> mono) {
                return mono;
            }
        };
    }
}