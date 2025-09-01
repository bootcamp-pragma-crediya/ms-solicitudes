package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationDataTest {

    @Test
    void shouldCreateLoanApplicationData() {
        LoanApplicationData data = LoanApplicationData.builder()
                .id("test-id")
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status("PENDING_REVIEW")
                .createdAt(OffsetDateTime.now())
                .build();

        assertNotNull(data);
        assertEquals("test-id", data.getId());
        assertEquals("12345678", data.getCustomerDocument());
        assertEquals(BigDecimal.valueOf(10000), data.getAmount());
        assertEquals(12, data.getTermMonths());
        assertEquals("PERSONAL", data.getLoanType());
        assertEquals("PENDING_REVIEW", data.getStatus());
        assertNotNull(data.getCreatedAt());
    }
}