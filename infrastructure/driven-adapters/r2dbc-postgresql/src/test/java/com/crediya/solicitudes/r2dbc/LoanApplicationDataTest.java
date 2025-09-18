package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationDataTest {

    @Test
    void constructor_ShouldCreateLoanApplicationData() {
        LocalDateTime now = LocalDateTime.now();
        LoanApplicationData data = new LoanApplicationData(
                "1", "user1", "12345678", "test@test.com", "John Doe",
                new BigDecimal("10000"), 12, "PERSONAL", new BigDecimal("5.5"),
                new BigDecimal("3000"), new BigDecimal("900"), "PENDING", now, true
        );

        assertEquals("1", data.getId());
        assertEquals("user1", data.getUserId());
        assertEquals("12345678", data.getCustomerDocument());
        assertEquals("test@test.com", data.getEmail());
        assertEquals("John Doe", data.getCustomerName());
        assertEquals(new BigDecimal("10000"), data.getAmount());
        assertEquals(12, data.getTermMonths());
        assertEquals("PERSONAL", data.getLoanType());
        assertEquals(new BigDecimal("5.5"), data.getInterestRate());
        assertEquals(new BigDecimal("3000"), data.getBaseSalary());
        assertEquals(new BigDecimal("900"), data.getMonthlyPayment());
        assertEquals("PENDING", data.getStatus());
        assertEquals(now, data.getCreatedAt());
        assertTrue(data.isNew());
    }

    @Test
    void builder_ShouldCreateLoanApplicationData() {
        LoanApplicationData data = LoanApplicationData.builder()
                .id("1")
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .build();

        assertEquals("1", data.getId());
        assertEquals("12345678", data.getCustomerDocument());
        assertEquals(new BigDecimal("10000"), data.getAmount());
        assertTrue(data.isNew());
    }

    @Test
    void markNotNew_ShouldSetIsNewToFalse() {
        LoanApplicationData data = LoanApplicationData.builder().build();
        assertTrue(data.isNew());

        data.markNotNew();
        assertFalse(data.isNew());
    }

    @Test
    void toBuilder_ShouldCreateCopyWithModifications() {
        LoanApplicationData original = LoanApplicationData.builder()
                .id("1")
                .customerDocument("12345678")
                .build();

        LoanApplicationData modified = original.toBuilder()
                .amount(new BigDecimal("20000"))
                .build();

        assertEquals("1", modified.getId());
        assertEquals("12345678", modified.getCustomerDocument());
        assertEquals(new BigDecimal("20000"), modified.getAmount());
    }

    @Test
    void setters_ShouldUpdateFields() {
        LoanApplicationData data = new LoanApplicationData();
        
        data.setId("1");
        data.setUserId("user1");
        data.setCustomerDocument("12345678");
        data.setEmail("test@test.com");
        data.setCustomerName("John Doe");
        data.setAmount(new BigDecimal("10000"));
        data.setTermMonths(12);
        data.setLoanType("PERSONAL");
        data.setInterestRate(new BigDecimal("5.5"));
        data.setBaseSalary(new BigDecimal("3000"));
        data.setMonthlyPayment(new BigDecimal("900"));
        data.setStatus("PENDING");
        data.setCreatedAt(LocalDateTime.now());

        assertEquals("1", data.getId());
        assertEquals("user1", data.getUserId());
        assertEquals("12345678", data.getCustomerDocument());
        assertEquals("test@test.com", data.getEmail());
        assertEquals("John Doe", data.getCustomerName());
        assertEquals(new BigDecimal("10000"), data.getAmount());
        assertEquals(12, data.getTermMonths());
        assertEquals("PERSONAL", data.getLoanType());
        assertEquals(new BigDecimal("5.5"), data.getInterestRate());
        assertEquals(new BigDecimal("3000"), data.getBaseSalary());
        assertEquals(new BigDecimal("900"), data.getMonthlyPayment());
        assertEquals("PENDING", data.getStatus());
        assertNotNull(data.getCreatedAt());
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        LoanApplicationData data1 = LoanApplicationData.builder()
                .id("1")
                .customerDocument("12345678")
                .build();

        LoanApplicationData data2 = LoanApplicationData.builder()
                .id("1")
                .customerDocument("12345678")
                .build();

        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        LoanApplicationData data = LoanApplicationData.builder()
                .id("1")
                .customerDocument("12345678")
                .build();

        String result = data.toString();
        assertNotNull(result);
        assertTrue(result.contains("LoanApplicationData"));
    }
}