package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanApplicationResponse;
import com.crediya.solicitudes.api.dto.LoanDtoMapper;
import com.crediya.solicitudes.model.exception.InvalidLoanApplicationException;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanApplicationHandlerTest {

    @Mock
    private LoanApplicationUseCase useCase;
    
    @Mock
    private LoanDtoMapper mapper;
    
    @Mock
    private ServerRequest serverRequest;

    private LoanApplicationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new LoanApplicationHandler(useCase, mapper);
    }

    @Test
    void shouldCreateLoanApplicationSuccessfully() {
        // Given
        var request = new CreateLoanRequestRequest("12345678", new BigDecimal("10000"), 12, "PERSONAL");
        var domainApp = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .build();
        var savedApp = domainApp.toBuilder()
                .id("generated-id")
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build();
        var response = new LoanApplicationResponse("generated-id", "PENDING_REVIEW", "12345678", new BigDecimal("10000"), 12, "PERSONAL", OffsetDateTime.now());

        when(serverRequest.bodyToMono(CreateLoanRequestRequest.class)).thenReturn(Mono.just(request));
        when(mapper.toDomain(request)).thenReturn(domainApp);
        when(useCase.execute(domainApp)).thenReturn(Mono.just(savedApp));
        when(mapper.toResponse(savedApp)).thenReturn(response);

        // When & Then
        StepVerifier.create(handler.create(serverRequest))
                .expectNextMatches(serverResponse -> 
                    serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void shouldHandleValidationError() {
        // Given
        var request = new CreateLoanRequestRequest("", new BigDecimal("10000"), 12, "PERSONAL");
        var domainApp = LoanApplication.builder()
                .customerDocument("")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .build();

        when(serverRequest.bodyToMono(CreateLoanRequestRequest.class)).thenReturn(Mono.just(request));
        when(mapper.toDomain(request)).thenReturn(domainApp);
        when(useCase.execute(domainApp)).thenReturn(Mono.error(new InvalidLoanApplicationException("customerDocument is required")));

        // When & Then
        StepVerifier.create(handler.create(serverRequest))
                .expectNextMatches(serverResponse -> 
                    serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }
}