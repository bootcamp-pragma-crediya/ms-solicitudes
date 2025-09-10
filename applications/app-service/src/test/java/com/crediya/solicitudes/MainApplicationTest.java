package com.crediya.solicitudes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("test")
class MainApplicationTest {

    @Test
    void contextLoads() {
        // Test that the Spring context loads successfully
    }
}