package com.crediya.solicitudes.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CreateLoanRequestRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        BigDecimal amount = BigDecimal.valueOf(10000);
        Integer termMonths = 12;
        String loanType = "PERSONAL";
        
        // When
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                amount, termMonths, loanType);
        
        // Then
        assertThat(request.amount()).isEqualTo(amount);
        assertThat(request.termMonths()).isEqualTo(termMonths);
        assertThat(request.loanType()).isEqualTo(loanType);
    }
    
    @Test
    void shouldSupportEqualsAndHashCode() {
        // Given
        CreateLoanRequestRequest request1 = new CreateLoanRequestRequest(
                BigDecimal.valueOf(10000), 12, "PERSONAL");
        CreateLoanRequestRequest request2 = new CreateLoanRequestRequest(
                BigDecimal.valueOf(10000), 12, "PERSONAL");
        
        // Then
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }
    
    @Test
    void shouldSupportToString() {
        // Given
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                BigDecimal.valueOf(10000), 12, "PERSONAL");
        
        // When
        String result = request.toString();
        
        // Then
        assertThat(result).contains("10000");
        assertThat(result).contains("12");
        assertThat(result).contains("PERSONAL");
    }
}