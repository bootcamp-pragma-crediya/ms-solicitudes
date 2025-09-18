package com.crediya.solicitudes.model.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidLoanTypeExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "Invalid loan type";
        InvalidLoanTypeException exception = new InvalidLoanTypeException(message);
        
        assertEquals(message, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void shouldCreateExceptionWithNullMessage() {
        InvalidLoanTypeException exception = new InvalidLoanTypeException(null);
        
        assertNull(exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithEmptyMessage() {
        String message = "";
        InvalidLoanTypeException exception = new InvalidLoanTypeException(message);
        
        assertEquals(message, exception.getMessage());
    }
}