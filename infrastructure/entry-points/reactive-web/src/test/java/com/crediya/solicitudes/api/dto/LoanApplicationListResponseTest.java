package com.crediya.solicitudes.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class LoanApplicationListResponseTest {

    @Test
    void shouldCreateResponseWithAllFields() {
        // Given
        String id = "1";
        BigDecimal amount = BigDecimal.valueOf(10000);
        Integer termMonths = 12;
        String email = "test@test.com";
        String customerName = "Test User";
        String loanType = "PERSONAL";
        BigDecimal interestRate = BigDecimal.valueOf(0.15);
        String status = "PENDING_REVIEW";
        BigDecimal baseSalary = BigDecimal.valueOf(5000);
        BigDecimal monthlyPayment = BigDecimal.valueOf(900);
        
        // When
        LoanApplicationListResponse response = new LoanApplicationListResponse(
                id, amount, termMonths, email, customerName, loanType, 
                interestRate, status, baseSalary, monthlyPayment);
        
        // Then
        assertThat(response.id()).isEqualTo(id);
        assertThat(response.amount()).isEqualTo(amount);
        assertThat(response.termMonths()).isEqualTo(termMonths);
        assertThat(response.email()).isEqualTo(email);
        assertThat(response.customerName()).isEqualTo(customerName);
        assertThat(response.loanType()).isEqualTo(loanType);
        assertThat(response.interestRate()).isEqualTo(interestRate);
        assertThat(response.status()).isEqualTo(status);
        assertThat(response.baseSalary()).isEqualTo(baseSalary);
        assertThat(response.monthlyPayment()).isEqualTo(monthlyPayment);
    }
    
    @Test
    void shouldSupportEqualsAndHashCode() {
        // Given
        LoanApplicationListResponse response1 = new LoanApplicationListResponse(
                "1", BigDecimal.valueOf(10000), 12, "test@test.com", "Test User", 
                "PERSONAL", BigDecimal.valueOf(0.15), "PENDING_REVIEW", 
                BigDecimal.valueOf(5000), BigDecimal.valueOf(900));
        LoanApplicationListResponse response2 = new LoanApplicationListResponse(
                "1", BigDecimal.valueOf(10000), 12, "test@test.com", "Test User", 
                "PERSONAL", BigDecimal.valueOf(0.15), "PENDING_REVIEW", 
                BigDecimal.valueOf(5000), BigDecimal.valueOf(900));
        
        // Then
        assertThat(response1).isEqualTo(response2);
        assertThat(response1.hashCode()).isEqualTo(response2.hashCode());
    }
    
    @Test
    void shouldSupportToString() {
        // Given
        LoanApplicationListResponse response = new LoanApplicationListResponse(
                "1", BigDecimal.valueOf(10000), 12, "test@test.com", "Test User", 
                "PERSONAL", BigDecimal.valueOf(0.15), "PENDING_REVIEW", 
                BigDecimal.valueOf(5000), BigDecimal.valueOf(900));
        
        // When
        String result = response.toString();
        
        // Then
        assertThat(result).contains("1");
        assertThat(result).contains("10000");
        assertThat(result).contains("test@test.com");
        assertThat(result).contains("Test User");
    }
}