package com.crediya.solicitudes.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    @Test
    void shouldCreateOpenAPI() {
        // Given
        OpenApiConfig config = new OpenApiConfig();
        
        // When
        OpenAPI openAPI = config.openAPI();
        
        // Then
        assertThat(openAPI).isNotNull();
        assertThat(openAPI.getInfo()).isNotNull();
        assertThat(openAPI.getInfo().getTitle()).isEqualTo("Loan Applications API");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("v1");
        assertThat(openAPI.getInfo().getDescription()).isEqualTo("Endpoints for loan application requests");
    }
}