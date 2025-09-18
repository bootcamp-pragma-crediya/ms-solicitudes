package com.crediya.solicitudes.model.loantype;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanTypeTest {

    @Test
    void shouldCreateLoanType() {
        LoanType loanType = LoanType.builder()
                .code("PERSONAL")
                .name("Personal Loan")
                .active(true)
                .build();

        assertNotNull(loanType);
        assertEquals("PERSONAL", loanType.getCode());
        assertEquals("Personal Loan", loanType.getName());
        assertTrue(loanType.isActive());
    }

    @Test
    void shouldCreateInactiveLoanType() {
        LoanType loanType = LoanType.builder()
                .code("MORTGAGE")
                .name("Mortgage Loan")
                .active(false)
                .build();

        assertEquals("MORTGAGE", loanType.getCode());
        assertEquals("Mortgage Loan", loanType.getName());
        assertFalse(loanType.isActive());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        LoanType loanType1 = LoanType.builder()
                .code("AUTO")
                .name("Auto Loan")
                .active(true)
                .build();

        LoanType loanType2 = LoanType.builder()
                .code("AUTO")
                .name("Auto Loan")
                .active(true)
                .build();

        assertEquals(loanType1, loanType2);
        assertEquals(loanType1.hashCode(), loanType2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        LoanType loanType = LoanType.builder()
                .code("STUDENT")
                .name("Student Loan")
                .active(true)
                .build();

        String toString = loanType.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("STUDENT"));
        assertTrue(toString.contains("Student Loan"));
        assertTrue(toString.contains("true"));
    }

    @Test
    void shouldCreateLoanTypeWithNullValues() {
        LoanType loanType = LoanType.builder()
                .code(null)
                .name(null)
                .active(false)
                .build();

        assertNull(loanType.getCode());
        assertNull(loanType.getName());
        assertFalse(loanType.isActive());
    }

    @Test
    void shouldCreateLoanTypeWithEmptyStrings() {
        LoanType loanType = LoanType.builder()
                .code("")
                .name("")
                .active(true)
                .build();

        assertEquals("", loanType.getCode());
        assertEquals("", loanType.getName());
        assertTrue(loanType.isActive());
    }
}