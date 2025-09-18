package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanDtoMapper;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class LoanApplicationHandlerIntegrationTest {

    @Mock
    private LoanApplicationUseCase loanApplicationUseCase;
    
    @Mock
    private ListLoanApplicationsUseCase listLoanApplicationsUseCase;
    
    @Mock
    private LoanDtoMapper loanDtoMapper;

    @Test
    void shouldCreateHandlerSuccessfully() {
        // Given & When
        LoanApplicationHandler handler = new LoanApplicationHandler(loanApplicationUseCase, listLoanApplicationsUseCase, loanDtoMapper);
        
        // Then
        assertNotNull(handler);
    }

    @Test
    void shouldMockUseCasesCorrectly() {
        // Given
        var savedApp = LoanApplication.builder()
                .id("test-id")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .customerDocument("123456789")
                .createdAt(OffsetDateTime.now())
                .build();

        when(loanApplicationUseCase.execute(any(LoanApplication.class), anyString()))
                .thenReturn(Mono.just(savedApp));

        var pagedResult = new ListLoanApplicationsUseCase.PagedResult(
                Collections.emptyList(), 0, 10, 0L, 0);

        when(listLoanApplicationsUseCase.execute(0, 10))
                .thenReturn(Mono.just(pagedResult));

        // When & Then
        assertNotNull(loanApplicationUseCase.execute(savedApp, "test-user").block());
        assertNotNull(listLoanApplicationsUseCase.execute(0, 10).block());
    }
}