package com.crediya.solicitudes.model.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidLoanApplicationExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "Invalid loan application";
        InvalidLoanApplicationException exception = new InvalidLoanApplicationException(message);
        
        assertEquals(message, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void shouldCreateExceptionWithNullMessage() {
        InvalidLoanApplicationException exception = new InvalidLoanApplicationException(null);
        
        assertNull(exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithEmptyMessage() {
        String message = "";
        InvalidLoanApplicationException exception = new InvalidLoanApplicationException(message);
        
        assertEquals(message, exception.getMessage());
    }
}