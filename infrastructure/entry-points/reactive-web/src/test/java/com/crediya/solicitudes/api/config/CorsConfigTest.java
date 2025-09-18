package com.crediya.solicitudes.api.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.cors.reactive.CorsWebFilter;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    @Test
    void shouldCreateCorsWebFilter() {
        CorsConfig corsConfig = new CorsConfig();
        String origins = "http://localhost:3000,https://example.com";
        
        CorsWebFilter filter = corsConfig.corsWebFilter(origins);
        
        assertNotNull(filter);
    }

    @Test
    void shouldCreateCorsWebFilterWithSingleOrigin() {
        CorsConfig corsConfig = new CorsConfig();
        String origins = "http://localhost:3000";
        
        CorsWebFilter filter = corsConfig.corsWebFilter(origins);
        
        assertNotNull(filter);
    }

    @Test
    void shouldCreateCorsWebFilterWithEmptyOrigin() {
        CorsConfig corsConfig = new CorsConfig();
        String origins = "";
        
        CorsWebFilter filter = corsConfig.corsWebFilter(origins);
        
        assertNotNull(filter);
    }

    @Test
    void shouldCreateCorsWebFilterWithMultipleOrigins() {
        CorsConfig corsConfig = new CorsConfig();
        String origins = "http://localhost:3000,https://app.example.com,https://admin.example.com";
        
        CorsWebFilter filter = corsConfig.corsWebFilter(origins);
        
        assertNotNull(filter);
    }
}