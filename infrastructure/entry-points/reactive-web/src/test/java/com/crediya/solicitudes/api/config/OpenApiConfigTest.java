package com.crediya.solicitudes.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    @Test
    void shouldCreateOpenAPI() {
        OpenApiConfig config = new OpenApiConfig();
        
        OpenAPI openAPI = config.openAPI();
        
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("Loan Applications API", openAPI.getInfo().getTitle());
        assertEquals("v1", openAPI.getInfo().getVersion());
        assertEquals("Endpoints for loan application requests", openAPI.getInfo().getDescription());
    }

    @Test
    void shouldHaveCorrectApiInfo() {
        OpenApiConfig config = new OpenApiConfig();
        
        OpenAPI openAPI = config.openAPI();
        
        assertNotNull(openAPI.getInfo().getTitle());
        assertNotNull(openAPI.getInfo().getVersion());
        assertNotNull(openAPI.getInfo().getDescription());
        assertFalse(openAPI.getInfo().getTitle().isEmpty());
        assertFalse(openAPI.getInfo().getVersion().isEmpty());
        assertFalse(openAPI.getInfo().getDescription().isEmpty());
    }
}