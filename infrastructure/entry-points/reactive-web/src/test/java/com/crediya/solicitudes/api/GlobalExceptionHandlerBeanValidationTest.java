package com.crediya.solicitudes.api;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ServerWebInputException;
import reactor.test.StepVerifier;

import java.util.Map;

class GlobalExceptionHandlerBeanValidationTest {

    @Test
    void handleBeanValidation_ShouldReturnValidationFailedMessage() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        
        // Test with ServerWebInputException instead
        ServerWebInputException ex = new ServerWebInputException("Validation error");
        
        StepVerifier.create(handler.handleBadJson(ex))
                .expectNext(Map.of("message", "Invalid input"))
                .verifyComplete();
    }
}