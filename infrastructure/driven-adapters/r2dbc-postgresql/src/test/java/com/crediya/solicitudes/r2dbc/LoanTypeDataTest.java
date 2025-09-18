package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanTypeDataTest {

    @Test
    void shouldCreateLoanTypeData() {
        LoanTypeData loanType = LoanTypeData.builder()
                .code("PERSONAL")
                .name("Personal Loan")
                .active(true)
                .build();

        assertEquals("PERSONAL", loanType.getCode());
        assertEquals("Personal Loan", loanType.getName());
        assertTrue(loanType.isActive());
    }

    @Test
    void shouldCreateEmptyLoanTypeData() {
        LoanTypeData loanType = new LoanTypeData();
        
        assertNull(loanType.getCode());
        assertNull(loanType.getName());
        assertFalse(loanType.isActive());
    }

    @Test
    void shouldCreateLoanTypeDataWithConstructor() {
        LoanTypeData loanType = new LoanTypeData("MORTGAGE", "Mortgage Loan", false);

        assertEquals("MORTGAGE", loanType.getCode());
        assertEquals("Mortgage Loan", loanType.getName());
        assertFalse(loanType.isActive());
    }

    @Test
    void shouldSupportSetters() {
        LoanTypeData loanType = new LoanTypeData();
        loanType.setCode("AUTO");
        loanType.setName("Auto Loan");
        loanType.setActive(true);

        assertEquals("AUTO", loanType.getCode());
        assertEquals("Auto Loan", loanType.getName());
        assertTrue(loanType.isActive());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        LoanTypeData loanType1 = LoanTypeData.builder()
                .code("BUSINESS")
                .name("Business Loan")
                .active(true)
                .build();

        LoanTypeData loanType2 = LoanTypeData.builder()
                .code("BUSINESS")
                .name("Business Loan")
                .active(true)
                .build();

        assertEquals(loanType1, loanType2);
        assertEquals(loanType1.hashCode(), loanType2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        LoanTypeData loanType = LoanTypeData.builder()
                .code("STUDENT")
                .name("Student Loan")
                .active(false)
                .build();

        String toString = loanType.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("STUDENT"));
        assertTrue(toString.contains("Student Loan"));
        assertTrue(toString.contains("false"));
    }

    @Test
    void shouldHandleNullValues() {
        LoanTypeData loanType = LoanTypeData.builder()
                .code(null)
                .name(null)
                .active(false)
                .build();

        assertNull(loanType.getCode());
        assertNull(loanType.getName());
        assertFalse(loanType.isActive());
    }
}