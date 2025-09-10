package com.crediya.solicitudes.api.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.cors.reactive.CorsWebFilter;

import static org.assertj.core.api.Assertions.assertThat;

class CorsConfigTest {

    @Test
    void shouldCreateCorsWebFilter() {
        // Given
        CorsConfig config = new CorsConfig();
        String origins = "http://localhost:3000,http://localhost:8080";
        
        // When
        CorsWebFilter filter = config.corsWebFilter(origins);
        
        // Then
        assertThat(filter).isNotNull();
    }
    
    @Test
    void shouldCreateCorsWebFilterWithSingleOrigin() {
        // Given
        CorsConfig config = new CorsConfig();
        String origins = "http://localhost:3000";
        
        // When
        CorsWebFilter filter = config.corsWebFilter(origins);
        
        // Then
        assertThat(filter).isNotNull();
    }
}