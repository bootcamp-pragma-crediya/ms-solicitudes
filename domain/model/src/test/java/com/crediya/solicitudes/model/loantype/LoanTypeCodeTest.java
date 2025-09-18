package com.crediya.solicitudes.model.loantype;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanTypeCodeTest {

    @Test
    void shouldHaveCorrectCodes() {
        assertEquals("PERSONAL", LoanTypeCode.PERSONAL.getCode());
        assertEquals("MORTGAGE", LoanTypeCode.MORTGAGE.getCode());
        assertEquals("AUTO", LoanTypeCode.AUTO.getCode());
        assertEquals("BUSINESS", LoanTypeCode.BUSINESS.getCode());
    }

    @Test
    void shouldHaveAllEnumValues() {
        LoanTypeCode[] values = LoanTypeCode.values();
        assertEquals(4, values.length);
        
        assertNotNull(LoanTypeCode.valueOf("PERSONAL"));
        assertNotNull(LoanTypeCode.valueOf("MORTGAGE"));
        assertNotNull(LoanTypeCode.valueOf("AUTO"));
        assertNotNull(LoanTypeCode.valueOf("BUSINESS"));
    }

    @Test
    void shouldReturnCorrectToString() {
        assertEquals("PERSONAL", LoanTypeCode.PERSONAL.toString());
        assertEquals("MORTGAGE", LoanTypeCode.MORTGAGE.toString());
        assertEquals("AUTO", LoanTypeCode.AUTO.toString());
        assertEquals("BUSINESS", LoanTypeCode.BUSINESS.toString());
    }

    @Test
    void shouldSupportOrdinal() {
        assertEquals(0, LoanTypeCode.PERSONAL.ordinal());
        assertEquals(1, LoanTypeCode.MORTGAGE.ordinal());
        assertEquals(2, LoanTypeCode.AUTO.ordinal());
        assertEquals(3, LoanTypeCode.BUSINESS.ordinal());
    }

    @Test
    void shouldSupportName() {
        assertEquals("PERSONAL", LoanTypeCode.PERSONAL.name());
        assertEquals("MORTGAGE", LoanTypeCode.MORTGAGE.name());
        assertEquals("AUTO", LoanTypeCode.AUTO.name());
        assertEquals("BUSINESS", LoanTypeCode.BUSINESS.name());
    }
}