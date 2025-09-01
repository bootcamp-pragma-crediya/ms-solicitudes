package com.crediya.solicitudes.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UseCasesConfigTest {

    @Test
    void testUseCasesConfigExists() {
        UseCasesConfig config = new UseCasesConfig();
        assertNotNull(config, "UseCasesConfig should be instantiable");
    }
}