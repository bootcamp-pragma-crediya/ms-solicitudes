package com.crediya.solicitudes.model.loanstatus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanStatusTest {

    @Test
    void shouldHaveCorrectValues() {
        assertEquals(4, LoanStatus.values().length);
        assertNotNull(LoanStatus.PENDING_REVIEW);
        assertNotNull(LoanStatus.APPROVED);
        assertNotNull(LoanStatus.REJECTED);
        assertNotNull(LoanStatus.MANUAL_REVIEW);
    }
}