package com.crediya.solicitudes.api;

import com.crediya.solicitudes.model.exception.InvalidLoanApplicationException;
import com.crediya.solicitudes.model.exception.InvalidLoanTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.test.StepVerifier;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Test
    void handleBeanValidation_ShouldReturnValidationFailedMessage() {
        ServerWebInputException ex = new ServerWebInputException("Validation error");
        
        StepVerifier.create(handler.handleBadJson(ex))
                .expectNext(Map.of("message", "Invalid input"))
                .verifyComplete();
    }

    @Test
    void handleBadJson_ShouldReturnInvalidInputMessage() {
        ServerWebInputException ex = new ServerWebInputException("Invalid JSON");
        
        StepVerifier.create(handler.handleBadJson(ex))
                .expectNext(Map.of("message", "Invalid input"))
                .verifyComplete();
    }

    @Test
    void handleDomain_WithInvalidLoanApplicationException_ShouldReturnExceptionMessage() {
        InvalidLoanApplicationException ex = new InvalidLoanApplicationException("Invalid loan");
        
        StepVerifier.create(handler.handleDomain(ex))
                .expectNext(Map.of("message", "Invalid loan"))
                .verifyComplete();
    }

    @Test
    void handleDomain_WithInvalidLoanTypeException_ShouldReturnExceptionMessage() {
        InvalidLoanTypeException ex = new InvalidLoanTypeException("Invalid type");
        
        StepVerifier.create(handler.handleDomain(ex))
                .expectNext(Map.of("message", "Invalid type"))
                .verifyComplete();
    }

    @Test
    void handleDomain_WithIllegalArgumentException_ShouldReturnExceptionMessage() {
        IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");
        
        StepVerifier.create(handler.handleDomain(ex))
                .expectNext(Map.of("message", "Illegal argument"))
                .verifyComplete();
    }

    @Test
    void handleAny_ShouldReturnUnexpectedErrorMessage() {
        RuntimeException ex = new RuntimeException("Unexpected error");
        
        StepVerifier.create(handler.handleAny(ex))
                .expectNext(Map.of("message", "An unexpected error occurred"))
                .verifyComplete();
    }
}