package com.crediya.solicitudes.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class JwtDebugHandlerTest {

    @InjectMocks
    private JwtDebugHandler handler;

    @Mock
    private ServerRequest request;

    @Test
    void disableJwtValidation_ShouldReturnSuccessMessage() {
        StepVerifier.create(handler.disableJwtValidation(request))
                .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
                .verifyComplete();
    }
}