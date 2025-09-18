package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationRepositoryAdapterTest {
    
    @Test
    void shouldCreateLoanApplicationData() {
        LoanApplicationData data = new LoanApplicationData();
        data.setId("1");
        data.setCustomerDocument("12345678");
        data.setAmount(BigDecimal.valueOf(10000));
        data.setStatus("PENDING_REVIEW");
        
        assertEquals("1", data.getId());
        assertEquals("12345678", data.getCustomerDocument());
        assertEquals(BigDecimal.valueOf(10000), data.getAmount());
        assertEquals("PENDING_REVIEW", data.getStatus());
    }
    
    @Test
    void shouldCreateLoanApplicationWithUserData() {
        LoanApplicationWithUserData data = new LoanApplicationWithUserData(
                "1", "user1", "12345678", BigDecimal.valueOf(10000), 12,
                "test@email.com", "PERSONAL", BigDecimal.valueOf(5.5),
                BigDecimal.valueOf(900), "PENDING_REVIEW", null,
                "Test", "User", BigDecimal.valueOf(3000000)
        );
        
        assertEquals("1", data.getId());
        assertEquals("user1", data.getUserId());
        assertEquals("Test", data.getUserName());
        assertEquals(BigDecimal.valueOf(3000000), data.getUserBaseSalary());
    }
}