package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MockLoanApplicationRepositoryAdapterTest {

    private final MockLoanApplicationRepositoryAdapter adapter = new MockLoanApplicationRepositoryAdapter();

    @Test
    void shouldSaveLoanApplicationWithGeneratedId() {
        LoanApplication application = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000.00"))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .build();

        StepVerifier.create(adapter.save(application))
                .assertNext(saved -> {
                    assertNotNull(saved.getId());
                    assertEquals("12345678", saved.getCustomerDocument());
                    assertEquals(new BigDecimal("10000.00"), saved.getAmount());
                    assertEquals(12, saved.getTermMonths());
                    assertEquals("PERSONAL", saved.getLoanType());
                    assertEquals(LoanStatus.PENDING_REVIEW, saved.getStatus());
                })
                .verifyComplete();
    }
}