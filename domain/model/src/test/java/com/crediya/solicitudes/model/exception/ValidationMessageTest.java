package com.crediya.solicitudes.model.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationMessageTest {

    @Test
    void shouldHaveCorrectMessages() {
        assertEquals("Body is required", ValidationMessage.BODY_REQUIRED.getMessage());
        assertEquals("customerDocument is required", ValidationMessage.CUSTOMER_DOCUMENT_REQUIRED.getMessage());
        assertEquals("amount must be > 0", ValidationMessage.AMOUNT_MUST_BE_POSITIVE.getMessage());
        assertEquals("termMonths must be > 0", ValidationMessage.TERM_MONTHS_MUST_BE_POSITIVE.getMessage());
        assertEquals("loanType is required", ValidationMessage.LOAN_TYPE_REQUIRED.getMessage());
        assertEquals("loanType is invalid or inactive", ValidationMessage.LOAN_TYPE_INVALID.getMessage());
    }

    @Test
    void shouldHaveAllEnumValues() {
        ValidationMessage[] values = ValidationMessage.values();
        assertEquals(6, values.length);
        
        assertNotNull(ValidationMessage.valueOf("BODY_REQUIRED"));
        assertNotNull(ValidationMessage.valueOf("CUSTOMER_DOCUMENT_REQUIRED"));
        assertNotNull(ValidationMessage.valueOf("AMOUNT_MUST_BE_POSITIVE"));
        assertNotNull(ValidationMessage.valueOf("TERM_MONTHS_MUST_BE_POSITIVE"));
        assertNotNull(ValidationMessage.valueOf("LOAN_TYPE_REQUIRED"));
        assertNotNull(ValidationMessage.valueOf("LOAN_TYPE_INVALID"));
    }

    @Test
    void shouldReturnCorrectToString() {
        assertEquals("BODY_REQUIRED", ValidationMessage.BODY_REQUIRED.toString());
        assertEquals("LOAN_TYPE_INVALID", ValidationMessage.LOAN_TYPE_INVALID.toString());
    }
}