package com.crediya.solicitudes.model.loantype;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanTypeTest {

    @Test
    void shouldCreateLoanType() {
        LoanType loanType = LoanType.builder()
                .code("PERSONAL")
                .name("Personal Loan")
                .active(true)
                .build();

        assertNotNull(loanType);
        assertEquals("PERSONAL", loanType.getCode());
        assertEquals("Personal Loan", loanType.getName());
        assertTrue(loanType.isActive());
    }
}