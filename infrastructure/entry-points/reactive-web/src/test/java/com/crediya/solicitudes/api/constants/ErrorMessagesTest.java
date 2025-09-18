package com.crediya.solicitudes.api.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorMessagesTest {

    @Test
    void shouldHaveCorrectConstants() {
        assertEquals("Validation failed", ErrorMessages.VALIDATION_FAILED);
        assertEquals("Invalid input", ErrorMessages.INVALID_INPUT);
        assertEquals("An unexpected error occurred", ErrorMessages.UNEXPECTED_ERROR);
        assertEquals("Bad Request", ErrorMessages.BAD_REQUEST);
        assertEquals("Internal Server Error", ErrorMessages.INTERNAL_SERVER_ERROR);
    }

    @Test
    void shouldThrowExceptionWhenInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            try {
                var constructor = ErrorMessages.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        });
    }
}