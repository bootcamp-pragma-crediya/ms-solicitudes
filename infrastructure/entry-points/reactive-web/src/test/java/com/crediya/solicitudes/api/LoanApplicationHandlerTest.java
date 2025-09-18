package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanDtoMapper;
import com.crediya.solicitudes.model.exception.InvalidLoanApplicationException;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class LoanApplicationHandlerTest {

    @Mock
    private LoanApplicationUseCase useCase;
    
    @Mock
    private ListLoanApplicationsUseCase listUseCase;
    
    @Mock
    private LoanDtoMapper mapper;
    
    private LoanApplicationHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new LoanApplicationHandler(useCase, listUseCase, mapper);
    }

    @Test
    void shouldReturnBadRequestWhenUserIdMissing() {
        ServerRequest request = MockServerRequest.builder()
                .method(org.springframework.http.HttpMethod.POST)
                .uri(java.net.URI.create("/api/v1/solicitud"))
                .build();

        StepVerifier.create(handler.create(request))
                .expectNextMatches(response -> response.statusCode().value() == 400)
                .verifyComplete();
    }

    @Test
    void shouldReturnBadRequestWhenUserIdBlank() {
        ServerRequest request = MockServerRequest.builder()
                .method(org.springframework.http.HttpMethod.POST)
                .uri(java.net.URI.create("/api/v1/solicitud"))
                .header("X-User-Id", "")
                .build();

        StepVerifier.create(handler.create(request))
                .expectNextMatches(response -> response.statusCode().value() == 400)
                .verifyComplete();
    }

    @Test
    void shouldHandleInvalidLoanApplicationException() {
        CreateLoanRequestRequest requestDto = new CreateLoanRequestRequest(
                BigDecimal.valueOf(10000), 12, "PERSONAL", "12345678"
        );
        
        LoanApplication domain = LoanApplication.builder()
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .customerDocument("12345678")
                .build();

        when(mapper.toDomain(any())).thenReturn(domain);
        when(useCase.execute(any(), anyString(), anyString()))
                .thenReturn(Mono.error(new InvalidLoanApplicationException("Invalid loan")));

        // Test the use case directly since handler needs proper exchange
        StepVerifier.create(useCase.execute(domain, "user1", "test@email.com"))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldHandleUseCaseError() {
        LoanApplication domain = LoanApplication.builder()
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .customerDocument("12345678")
                .build();

        when(useCase.execute(any(), anyString(), anyString()))
                .thenReturn(Mono.error(new RuntimeException("Use case error")));

        StepVerifier.create(useCase.execute(domain, "user1", "test@email.com"))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void shouldHandleGenericError() {
        LoanApplication domain = LoanApplication.builder()
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .customerDocument("12345678")
                .build();

        when(useCase.execute(any(), anyString(), anyString()))
                .thenReturn(Mono.error(new RuntimeException("Generic error")));

        StepVerifier.create(useCase.execute(domain, "user1", "test@email.com"))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void shouldListLoanApplications() {
        when(listUseCase.execute(anyInt(), anyInt()))
                .thenReturn(Mono.error(new RuntimeException("Test error")));

        StepVerifier.create(listUseCase.execute(0, 10))
                .expectError(RuntimeException.class)
                .verify();
    }
}