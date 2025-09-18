package com.crediya.solicitudes.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationListResponseTest {

    @Test
    void shouldCreateLoanApplicationListResponse() {
        LoanApplicationListResponse response = new LoanApplicationListResponse(
                "1", "user1", "12345678", BigDecimal.valueOf(10000), 12,
                "test@email.com", "Test User", "PERSONAL", "PENDING_REVIEW", 
                BigDecimal.valueOf(3000000)
        );

        assertEquals("1", response.getId());
        assertEquals("user1", response.getUserId());
        assertEquals("12345678", response.getCustomerDocument());
        assertEquals(BigDecimal.valueOf(10000), response.getAmount());
        assertEquals(12, response.getTermMonths());
        assertEquals("test@email.com", response.getEmail());
        assertEquals("Test User", response.getCustomerName());
        assertEquals("PERSONAL", response.getLoanType());
        assertEquals("PENDING_REVIEW", response.getStatus());
        assertEquals(BigDecimal.valueOf(3000000), response.getBaseSalary());
    }

    @Test
    void shouldCreateEmptyResponse() {
        LoanApplicationListResponse response = new LoanApplicationListResponse();
        
        assertNull(response.getId());
        assertNull(response.getUserId());
        assertNull(response.getCustomerDocument());
    }

    @Test
    void shouldSetAndGetValues() {
        LoanApplicationListResponse response = new LoanApplicationListResponse();
        response.setId("1");
        response.setUserId("user1");
        response.setAmount(BigDecimal.valueOf(5000));
        
        assertEquals("1", response.getId());
        assertEquals("user1", response.getUserId());
        assertEquals(BigDecimal.valueOf(5000), response.getAmount());
    }
}