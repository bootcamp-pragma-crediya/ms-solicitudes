package com.crediya.solicitudes.model.loanapplication;

import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationTest {

    @Test
    void shouldCreateLoanApplication() {
        LoanApplication loan = LoanApplication.builder()
                .id("123")
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build();

        assertNotNull(loan);
        assertEquals("123", loan.getId());
        assertEquals("12345678", loan.getCustomerDocument());
        assertEquals(BigDecimal.valueOf(10000), loan.getAmount());
        assertEquals(12, loan.getTermMonths());
        assertEquals("PERSONAL", loan.getLoanType());
        assertEquals(LoanStatus.PENDING_REVIEW, loan.getStatus());
        assertNotNull(loan.getCreatedAt());
    }
}