package com.crediya.solicitudes.model.loanapplication;

import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationTest {

    @Test
    void shouldCreateLoanApplication() {
        LoanApplication loan = LoanApplication.builder()
                .id("123")
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build();

        assertNotNull(loan);
        assertEquals("123", loan.getId());
        assertEquals("12345678", loan.getCustomerDocument());
        assertEquals(BigDecimal.valueOf(10000), loan.getAmount());
        assertEquals(12, loan.getTermMonths());
        assertEquals("PERSONAL", loan.getLoanType());
        assertEquals(LoanStatus.PENDING_REVIEW, loan.getStatus());
        assertNotNull(loan.getCreatedAt());
    }

    @Test
    void shouldCreateLoanApplicationWithAllFields() {
        OffsetDateTime now = OffsetDateTime.now();
        LoanApplication loan = LoanApplication.builder()
                .id("456")
                .customerDocument("87654321")
                .email("test@example.com")
                .customerName("John Doe")
                .amount(BigDecimal.valueOf(50000))
                .termMonths(24)
                .loanType("MORTGAGE")
                .interestRate(BigDecimal.valueOf(5.5))
                .baseSalary(BigDecimal.valueOf(3000))
                .monthlyPayment(BigDecimal.valueOf(2500))
                .status(LoanStatus.APPROVED)
                .createdAt(now)
                .build();

        assertEquals("456", loan.getId());
        assertEquals("87654321", loan.getCustomerDocument());
        assertEquals("test@example.com", loan.getEmail());
        assertEquals("John Doe", loan.getCustomerName());
        assertEquals(BigDecimal.valueOf(50000), loan.getAmount());
        assertEquals(24, loan.getTermMonths());
        assertEquals("MORTGAGE", loan.getLoanType());
        assertEquals(BigDecimal.valueOf(5.5), loan.getInterestRate());
        assertEquals(BigDecimal.valueOf(3000), loan.getBaseSalary());
        assertEquals(BigDecimal.valueOf(2500), loan.getMonthlyPayment());
        assertEquals(LoanStatus.APPROVED, loan.getStatus());
        assertEquals(now, loan.getCreatedAt());
    }

    @Test
    void shouldCreateEmptyLoanApplication() {
        LoanApplication loan = new LoanApplication();
        assertNotNull(loan);
        assertNull(loan.getId());
        assertNull(loan.getCustomerDocument());
        assertNull(loan.getAmount());
    }

    @Test
    void shouldCreateLoanApplicationWithConstructor() {
        OffsetDateTime now = OffsetDateTime.now();
        LoanApplication loan = new LoanApplication(
                "789", "user-id", "11111111", "jane@example.com", "Jane Smith",
                BigDecimal.valueOf(25000), 18, "AUTO",
                BigDecimal.valueOf(4.5), BigDecimal.valueOf(4000),
                BigDecimal.valueOf(1500), LoanStatus.PENDING_REVIEW, now
        );

        assertEquals("789", loan.getId());
        assertEquals("11111111", loan.getCustomerDocument());
        assertEquals("jane@example.com", loan.getEmail());
        assertEquals("Jane Smith", loan.getCustomerName());
        assertEquals(BigDecimal.valueOf(25000), loan.getAmount());
        assertEquals(18, loan.getTermMonths());
        assertEquals("AUTO", loan.getLoanType());
        assertEquals(BigDecimal.valueOf(4.5), loan.getInterestRate());
        assertEquals(BigDecimal.valueOf(4000), loan.getBaseSalary());
        assertEquals(BigDecimal.valueOf(1500), loan.getMonthlyPayment());
        assertEquals(LoanStatus.PENDING_REVIEW, loan.getStatus());
        assertEquals(now, loan.getCreatedAt());
    }

    @Test
    void shouldSupportToBuilder() {
        LoanApplication original = LoanApplication.builder()
                .id("123")
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .build();

        LoanApplication modified = original.toBuilder()
                .amount(BigDecimal.valueOf(15000))
                .termMonths(36)
                .build();

        assertEquals("123", modified.getId());
        assertEquals("12345678", modified.getCustomerDocument());
        assertEquals(BigDecimal.valueOf(15000), modified.getAmount());
        assertEquals(36, modified.getTermMonths());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        LoanApplication loan1 = LoanApplication.builder()
                .id("123")
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .build();

        LoanApplication loan2 = LoanApplication.builder()
                .id("123")
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .build();

        assertEquals(loan1, loan2);
        assertEquals(loan1.hashCode(), loan2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        LoanApplication loan = LoanApplication.builder()
                .id("123")
                .customerDocument("12345678")
                .build();

        String toString = loan.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("123"));
        assertTrue(toString.contains("12345678"));
    }
}