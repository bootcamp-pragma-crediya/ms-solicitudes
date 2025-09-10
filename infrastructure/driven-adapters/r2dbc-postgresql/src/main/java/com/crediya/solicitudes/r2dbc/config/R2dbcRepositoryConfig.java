package com.crediya.solicitudes.r2dbc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@ConditionalOnProperty(name = "app.mock-mode", havingValue = "false", matchIfMissing = false)
@EnableR2dbcRepositories(basePackages = "com.crediya.solicitudes.r2dbc")
public class R2dbcRepositoryConfig {
}