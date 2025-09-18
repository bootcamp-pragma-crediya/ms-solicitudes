package com.crediya.solicitudes.api.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogMessagesTest {

    @Test
    void shouldHaveCorrectConstants() {
        assertEquals("[Error] Bean validation: {}", LogMessages.ERROR_BEAN_VALIDATION);
        assertEquals("[Error] Bad input: {}", LogMessages.ERROR_BAD_INPUT);
        assertEquals("[Error] {}", LogMessages.ERROR_DOMAIN);
        assertEquals("[Error] Unexpected", LogMessages.ERROR_UNEXPECTED);
        assertEquals("[Handler] POST /api/v1/solicitud doc={} type={} amount={} term={}", LogMessages.HANDLER_POST_REQUEST);
        assertEquals("[Handler] Created id={} status={}", LogMessages.HANDLER_CREATED);
        assertEquals("[Handler] Error processing request: {}", LogMessages.HANDLER_ERROR);
    }

    @Test
    void shouldThrowExceptionWhenInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            try {
                var constructor = LogMessages.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        });
    }
}