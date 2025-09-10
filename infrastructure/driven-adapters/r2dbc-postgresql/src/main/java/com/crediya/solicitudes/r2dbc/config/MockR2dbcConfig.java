package com.crediya.solicitudes.r2dbc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;

import static org.mockito.Mockito.mock;

@Configuration
@ConditionalOnProperty(name = "app.mock-mode", havingValue = "true", matchIfMissing = true)
public class MockR2dbcConfig {

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate() {
        return mock(R2dbcEntityTemplate.class);
    }
    
    @Bean
    public DatabaseClient databaseClient() {
        return mock(DatabaseClient.class);
    }
}