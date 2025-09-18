package com.crediya.solicitudes.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtDebugHandlerTest {

    @Mock
    private ServerRequest serverRequest;

    @InjectMocks
    private JwtDebugHandler jwtDebugHandler;

    @Test
    void shouldDisableJwtValidation() {
        // When
        Mono<ServerResponse> response = jwtDebugHandler.disableJwtValidation(serverRequest);

        // Then
        StepVerifier.create(response)
                .assertNext(serverResponse -> {
                    assertNotNull(serverResponse);
                    assertEquals(200, serverResponse.statusCode().value());
                })
                .verifyComplete();

        // Verify system property was set
        assertEquals("true", System.getProperty("jwt.validation.disabled"));
    }

    @Test
    void shouldReturnOkStatus() {
        // When
        Mono<ServerResponse> response = jwtDebugHandler.disableJwtValidation(serverRequest);

        // Then
        assertNotNull(response);
        
        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldHandleMultipleCalls() {
        // When
        Mono<ServerResponse> response1 = jwtDebugHandler.disableJwtValidation(serverRequest);
        Mono<ServerResponse> response2 = jwtDebugHandler.disableJwtValidation(serverRequest);

        // Then
        StepVerifier.create(response1)
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(response2)
                .expectNextCount(1)
                .verifyComplete();

        assertEquals("true", System.getProperty("jwt.validation.disabled"));
    }
}