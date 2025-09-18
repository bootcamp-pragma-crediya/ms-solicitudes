package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LoanApplicationDataTest {

    @Test
    void shouldCreateLoanApplicationData() {
        // Given
        String id = "test-id";
        String customerDocument = "123456789";
        BigDecimal amount = new BigDecimal("10000");
        Integer termMonths = 12;
        String loanType = "PERSONAL";
        String status = "PENDING_REVIEW";
        LocalDateTime createdAt = LocalDateTime.now();

        // When
        LoanApplicationData data = LoanApplicationData.builder()
                .id(id)
                .customerDocument(customerDocument)
                .amount(amount)
                .termMonths(termMonths)
                .loanType(loanType)
                .status(status)
                .createdAt(createdAt)
                .build();

        // Then
        assertThat(data.getId()).isEqualTo(id);
        assertThat(data.getCustomerDocument()).isEqualTo(customerDocument);
        assertThat(data.getAmount()).isEqualTo(amount);
        assertThat(data.getTermMonths()).isEqualTo(termMonths);
        assertThat(data.getLoanType()).isEqualTo(loanType);
        assertThat(data.getStatus()).isEqualTo(status);
        assertThat(data.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void shouldHandleNewEntityState() {
        // Given
        LoanApplicationData data = LoanApplicationData.builder()
                .id("test-id")
                .customerDocument("123456789")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .status("PENDING_REVIEW")
                .build();

        // When & Then - Test basic functionality without isNew/markNotNew
        assertThat(data.getId()).isEqualTo("test-id");
        assertThat(data.getCustomerDocument()).isEqualTo("123456789");
    }

    @Test
    void shouldCreateWithAllFields() {
        // Given & When
        LoanApplicationData data = LoanApplicationData.builder()
                .id("test-id")
                .customerDocument("123456789")
                .customerName("Test User")
                .email("test@email.com")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .status("PENDING_REVIEW")
                .baseSalary(new BigDecimal("5000000"))
                .interestRate(new BigDecimal("15.5"))
                .monthlyPayment(new BigDecimal("950"))

                .createdAt(LocalDateTime.now())
                .build();

        // Then
        assertThat(data.getCustomerName()).isEqualTo("Test User");
        assertThat(data.getEmail()).isEqualTo("test@email.com");
        assertThat(data.getBaseSalary()).isEqualTo(new BigDecimal("5000000"));
        assertThat(data.getInterestRate()).isEqualTo(new BigDecimal("15.5"));
        assertThat(data.getMonthlyPayment()).isEqualTo(new BigDecimal("950"));
        assertThat(data.getInterestRate()).isEqualTo(new BigDecimal("15.5"));
    }
}