package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanApplicationRepositoryAdapterTest {

    @Mock
    private LoanApplicationRepository repository;

    private LoanApplicationRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new LoanApplicationRepositoryAdapter(repository, null);
    }

    @Test
    void shouldSaveLoanApplication() {
        // Given
        var application = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build();

        var data = LoanApplicationData.builder()
                .id("generated-id")
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .status("PENDING_REVIEW")
                .createdAt(OffsetDateTime.now())
                .build();

        when(repository.save(any(LoanApplicationData.class))).thenReturn(Mono.just(data));

        // When & Then
        StepVerifier.create(adapter.save(application))
                .expectNextMatches(saved -> 
                    saved.getCustomerDocument().equals("12345678") &&
                    saved.getAmount().equals(new BigDecimal("10000")))
                .verifyComplete();
    }
}