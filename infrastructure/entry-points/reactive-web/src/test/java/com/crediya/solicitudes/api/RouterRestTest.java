package com.crediya.solicitudes.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RouterRestTest {

    @InjectMocks
    private RouterRest routerRest;

    @Mock
    private LoanApplicationHandler loanApplicationHandler;

    @Mock
    private JwtDebugHandler jwtDebugHandler;

    @Test
    void loanApplicationRoutes_ShouldReturnRouterFunction() {
        RouterFunction<ServerResponse> routes = routerRest.loanApplicationRoutes(loanApplicationHandler);
        assertNotNull(routes);
    }

    @Test
    void jwtConfigRoutes_ShouldReturnRouterFunction() {
        RouterFunction<ServerResponse> routes = routerRest.jwtConfigRoutes(jwtDebugHandler);
        assertNotNull(routes);
    }
}