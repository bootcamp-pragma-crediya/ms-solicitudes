package com.crediya.solicitudes.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LoanApplicationResponseTest {

    @Test
    void shouldCreateResponseWithAllFields() {
        // Given
        String id = "1";
        String status = "PENDING_REVIEW";
        String customerDocument = "12345678";
        BigDecimal amount = BigDecimal.valueOf(10000);
        Integer termMonths = 12;
        String loanType = "PERSONAL";
        OffsetDateTime createdAt = OffsetDateTime.now();
        
        // When
        LoanApplicationResponse response = new LoanApplicationResponse(
                id, status, customerDocument, amount, termMonths, loanType, createdAt);
        
        // Then
        assertThat(response.id()).isEqualTo(id);
        assertThat(response.status()).isEqualTo(status);
        assertThat(response.customerDocument()).isEqualTo(customerDocument);
        assertThat(response.amount()).isEqualTo(amount);
        assertThat(response.termMonths()).isEqualTo(termMonths);
        assertThat(response.loanType()).isEqualTo(loanType);
        assertThat(response.createdAt()).isEqualTo(createdAt);
    }
    
    @Test
    void shouldSupportEqualsAndHashCode() {
        // Given
        OffsetDateTime now = OffsetDateTime.now();
        LoanApplicationResponse response1 = new LoanApplicationResponse(
                "1", "PENDING_REVIEW", "12345678", BigDecimal.valueOf(10000), 12, "PERSONAL", now);
        LoanApplicationResponse response2 = new LoanApplicationResponse(
                "1", "PENDING_REVIEW", "12345678", BigDecimal.valueOf(10000), 12, "PERSONAL", now);
        
        // Then
        assertThat(response1).isEqualTo(response2);
        assertThat(response1.hashCode()).isEqualTo(response2.hashCode());
    }
    
    @Test
    void shouldSupportToString() {
        // Given
        LoanApplicationResponse response = new LoanApplicationResponse(
                "1", "PENDING_REVIEW", "12345678", BigDecimal.valueOf(10000), 
                12, "PERSONAL", OffsetDateTime.now());
        
        // When
        String result = response.toString();
        
        // Then
        assertThat(result).contains("1");
        assertThat(result).contains("PENDING_REVIEW");
        assertThat(result).contains("12345678");
        assertThat(result).contains("10000");
    }
}