package com.crediya.solicitudes.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RouterRestTest {

    @Mock
    private LoanApplicationHandler handler;
    
    private RouterRest routerRest;
    
    @BeforeEach
    void setUp() {
        routerRest = new RouterRest();
    }
    
    @Test
    void shouldCreateLoanApplicationRoutes() {
        // When
        RouterFunction<ServerResponse> routes = routerRest.loanApplicationRoutes(handler);
        
        // Then
        assertThat(routes).isNotNull();
    }
}