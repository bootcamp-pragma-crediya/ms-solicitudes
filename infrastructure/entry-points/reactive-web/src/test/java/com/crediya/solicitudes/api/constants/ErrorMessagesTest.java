package com.crediya.solicitudes.api.constants;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ErrorMessagesTest {

    @Test
    void shouldHaveCorrectErrorMessages() {
        assertThat(ErrorMessages.VALIDATION_FAILED).isEqualTo("Validation failed");
        assertThat(ErrorMessages.INVALID_INPUT).isEqualTo("Invalid input");
        assertThat(ErrorMessages.UNEXPECTED_ERROR).isEqualTo("An unexpected error occurred");
        assertThat(ErrorMessages.BAD_REQUEST).isEqualTo("Bad Request");
        assertThat(ErrorMessages.INTERNAL_SERVER_ERROR).isEqualTo("Internal Server Error");
    }

    @Test
    void shouldNotAllowInstantiation() {
        // Test that the class has a private constructor by checking it exists
        assertThat(ErrorMessages.class.getDeclaredConstructors()).hasSize(1);
        assertThat(ErrorMessages.class.getDeclaredConstructors()[0].getModifiers() & java.lang.reflect.Modifier.PRIVATE).isNotZero();
    }
}