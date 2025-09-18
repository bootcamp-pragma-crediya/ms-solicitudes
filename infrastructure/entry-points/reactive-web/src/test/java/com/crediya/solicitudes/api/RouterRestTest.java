package com.crediya.solicitudes.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RouterRestTest {

    @Mock
    private LoanApplicationHandler loanApplicationHandler;

    @Mock
    private JwtDebugHandler jwtDebugHandler;

    @Test
    void shouldCreateLoanApplicationRoutes() {
        RouterRest routerRest = new RouterRest();
        
        RouterFunction<ServerResponse> routes = routerRest.loanApplicationRoutes(loanApplicationHandler);
        
        assertNotNull(routes);
    }

    @Test
    void shouldCreateJwtConfigRoutes() {
        RouterRest routerRest = new RouterRest();
        
        RouterFunction<ServerResponse> routes = routerRest.jwtConfigRoutes(jwtDebugHandler);
        
        assertNotNull(routes);
    }

    @Test
    void shouldCreateRouterRestInstance() {
        RouterRest routerRest = new RouterRest();
        
        assertNotNull(routerRest);
    }

    @Test
    void shouldCreateBothRouteTypes() {
        RouterRest routerRest = new RouterRest();
        
        RouterFunction<ServerResponse> loanRoutes = routerRest.loanApplicationRoutes(loanApplicationHandler);
        RouterFunction<ServerResponse> jwtRoutes = routerRest.jwtConfigRoutes(jwtDebugHandler);
        
        assertNotNull(loanRoutes);
        assertNotNull(jwtRoutes);
        assertNotSame(loanRoutes, jwtRoutes);
    }
}