package com.crediya.solicitudes.api.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LogMessagesTest {

    @Test
    void shouldHaveCorrectLogMessages() {
        assertThat(LogMessages.ERROR_BEAN_VALIDATION).isEqualTo("[Error] Bean validation: {}");
        assertThat(LogMessages.ERROR_BAD_INPUT).isEqualTo("[Error] Bad input: {}");
        assertThat(LogMessages.ERROR_DOMAIN).isEqualTo("[Error] {}");
        assertThat(LogMessages.ERROR_UNEXPECTED).isEqualTo("[Error] Unexpected");
        assertThat(LogMessages.HANDLER_POST_REQUEST).isEqualTo("[Handler] POST /api/v1/solicitud doc={} type={} amount={} term={}");
        assertThat(LogMessages.HANDLER_CREATED).isEqualTo("[Handler] Created id={} status={}");
        assertThat(LogMessages.HANDLER_ERROR).isEqualTo("[Handler] Error processing request: {}");
    }
    
    @Test
    void shouldNotBeInstantiable() throws Exception {
        Constructor<LogMessages> constructor = LogMessages.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        
        assertThatThrownBy(constructor::newInstance)
                .hasCauseInstanceOf(UnsupportedOperationException.class);
    }
}