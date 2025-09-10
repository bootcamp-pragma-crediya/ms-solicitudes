package com.crediya.solicitudes.api.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
    void shouldNotBeInstantiable() throws Exception {
        Constructor<ErrorMessages> constructor = ErrorMessages.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        
        assertThatThrownBy(constructor::newInstance)
                .hasCauseInstanceOf(UnsupportedOperationException.class);
    }
}