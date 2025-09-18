package com.crediya.solicitudes.api.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CreateLoanRequestTest {

    @Test
    void shouldCreateRequest() {
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                BigDecimal.valueOf(10000), 12, "PERSONAL", "12345678"
        );
        
        assertEquals(BigDecimal.valueOf(10000), request.amount());
        assertEquals(12, request.termMonths());
        assertEquals("PERSONAL", request.loanType());
        assertEquals("12345678", request.customerDocument());
    }
    
    @Test
    void shouldSupportEquality() {
        CreateLoanRequestRequest request1 = new CreateLoanRequestRequest(
                BigDecimal.valueOf(10000), 12, "PERSONAL", "12345678"
        );
        CreateLoanRequestRequest request2 = new CreateLoanRequestRequest(
                BigDecimal.valueOf(10000), 12, "PERSONAL", "12345678"
        );
        
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }
}