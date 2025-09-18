package com.crediya.solicitudes.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationResponseTest {

    @Test
    void shouldCreateResponse() {
        OffsetDateTime now = OffsetDateTime.now();
        LoanApplicationResponse response = new LoanApplicationResponse(
                "1",
                "user1",
                "PENDING_REVIEW",
                "12345678",
                BigDecimal.valueOf(10000),
                12,
                "PERSONAL",
                now
        );

        assertEquals("1", response.id());
        assertEquals("user1", response.userId());
        assertEquals("PENDING_REVIEW", response.status());
        assertEquals("12345678", response.customerDocument());
        assertEquals(BigDecimal.valueOf(10000), response.amount());
        assertEquals(12, response.termMonths());
        assertEquals("PERSONAL", response.loanType());
        assertEquals(now, response.createdAt());
    }

    @Test
    void shouldBeEqualWhenSameValues() {
        OffsetDateTime now = OffsetDateTime.now();
        LoanApplicationResponse response1 = new LoanApplicationResponse(
                "1", "user1", "PENDING_REVIEW", "12345678", 
                BigDecimal.valueOf(10000), 12, "PERSONAL", now
        );
        LoanApplicationResponse response2 = new LoanApplicationResponse(
                "1", "user1", "PENDING_REVIEW", "12345678", 
                BigDecimal.valueOf(10000), 12, "PERSONAL", now
        );

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
}