package com.crediya.solicitudes.api;

import com.crediya.solicitudes.model.exception.InvalidLoanApplicationException;
import com.crediya.solicitudes.model.exception.InvalidLoanTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void shouldHandleInvalidLoanApplicationException() {
        var exception = new InvalidLoanApplicationException("Test error");

        StepVerifier.create(handler.handleDomain(exception))
                .expectNextMatches(response -> 
                    response.get("message").equals("Test error"))
                .verifyComplete();
    }

    @Test
    void shouldHandleInvalidLoanTypeException() {
        var exception = new InvalidLoanTypeException("Invalid loan type");

        StepVerifier.create(handler.handleDomain(exception))
                .expectNextMatches(response -> 
                    response.get("message").equals("Invalid loan type"))
                .verifyComplete();
    }

    @Test
    void shouldHandleUnexpectedError() {
        var exception = new RuntimeException("Unexpected error");

        StepVerifier.create(handler.handleAny(exception))
                .expectNextMatches(response -> 
                    response.get("message").equals("An unexpected error occurred"))
                .verifyComplete();
    }

    @Test
    void shouldHandleBeanValidation() {
        var exception = new IllegalArgumentException("Validation error");

        StepVerifier.create(handler.handleDomain(exception))
                .expectNextMatches(response -> 
                    response.get("message").equals("Validation error"))
                .verifyComplete();
    }

    @Test
    void shouldHandleBadJson() {
        var exception = new ServerWebInputException("Bad JSON");

        StepVerifier.create(handler.handleBadJson(exception))
                .expectNextMatches(response -> 
                    response.get("message").equals(com.crediya.solicitudes.api.constants.ErrorMessages.INVALID_INPUT))
                .verifyComplete();
    }
}