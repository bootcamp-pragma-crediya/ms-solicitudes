package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.dto.LoanDtoMapper;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class LoanApplicationHandlerCoverageTest {

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
    void shouldHandleSuccessfulLoanCreation() {
        LoanApplication domain = LoanApplication.builder()
                .id("1")
                .userId("user1")
                .status(LoanStatus.PENDING_REVIEW)
                .customerDocument("12345678")
                .customerName("Test User")
                .baseSalary(BigDecimal.valueOf(3000000))
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .createdAt(OffsetDateTime.now())
                .build();

        when(useCase.execute(any(), anyString(), anyString()))
                .thenReturn(Mono.just(domain));

        StepVerifier.create(useCase.execute(domain, "user1", "test@email.com"))
                .expectNext(domain)
                .verifyComplete();
    }

    @Test
    void shouldHandleListApplicationsSuccess() {
        var result = new ListLoanApplicationsUseCase.PagedResult(
                List.of(), 0, 10, 0L, 0
        );

        when(listUseCase.execute(anyInt(), anyInt()))
                .thenReturn(Mono.just(result));
        when(mapper.toListResponse(anyList()))
                .thenReturn(List.of());

        StepVerifier.create(listUseCase.execute(0, 10))
                .expectNext(result)
                .verifyComplete();
    }

    @Test
    void shouldHandleJsonProcessingException() {
        when(useCase.execute(any(), anyString(), anyString()))
                .thenReturn(Mono.error(new com.fasterxml.jackson.core.JsonProcessingException("JSON error") {}));

        StepVerifier.create(useCase.execute(null, "user1", "test@email.com"))
                .expectError(com.fasterxml.jackson.core.JsonProcessingException.class)
                .verify();
    }
}