package com.crediya.solicitudes.api;

import com.crediya.solicitudes.model.exception.InvalidLoanApplicationException;
import com.crediya.solicitudes.model.exception.InvalidLoanTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.crediya.solicitudes.api")
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Map<String, Object>> handleBeanValidation(WebExchangeBindException ex) {
        log.warn("[Error] Bean validation: {}", ex.getMessage());
        return Mono.just(Map.of("message", "Validation failed"));
    }

    @ExceptionHandler(ServerWebInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Map<String, Object>> handleBadJson(ServerWebInputException ex) {
        log.warn("[Error] Bad input: {}", ex.getReason());
        return Mono.just(Map.of("message", "Invalid input"));
    }

    @ExceptionHandler({InvalidLoanApplicationException.class, InvalidLoanTypeException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Map<String, Object>> handleDomain(RuntimeException ex) {
        log.warn("[Error] {}", ex.getMessage());
        return Mono.just(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Map<String, Object>> handleAny(Throwable ex) {
        log.error("[Error] Unexpected", ex);
        return Mono.just(Map.of("message", "An unexpected error occurred"));
    }
}