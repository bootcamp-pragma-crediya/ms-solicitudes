package com.crediya.solicitudes.r2dbc.config;

import org.junit.jupiter.api.Test;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;

import static org.junit.jupiter.api.Assertions.*;

class MockR2dbcConfigTest {

    @Test
    void shouldCreateR2dbcEntityTemplate() {
        MockR2dbcConfig config = new MockR2dbcConfig();
        
        R2dbcEntityTemplate template = config.r2dbcEntityTemplate();
        
        assertNotNull(template);
    }

    @Test
    void shouldCreateDatabaseClient() {
        MockR2dbcConfig config = new MockR2dbcConfig();
        
        DatabaseClient client = config.databaseClient();
        
        assertNotNull(client);
    }

    @Test
    void shouldCreateDifferentInstances() {
        MockR2dbcConfig config = new MockR2dbcConfig();
        
        R2dbcEntityTemplate template1 = config.r2dbcEntityTemplate();
        R2dbcEntityTemplate template2 = config.r2dbcEntityTemplate();
        
        assertNotNull(template1);
        assertNotNull(template2);
        // Each call creates a new mock instance
        assertNotSame(template1, template2);
    }

    @Test
    void shouldCreateDifferentDatabaseClientInstances() {
        MockR2dbcConfig config = new MockR2dbcConfig();
        
        DatabaseClient client1 = config.databaseClient();
        DatabaseClient client2 = config.databaseClient();
        
        assertNotNull(client1);
        assertNotNull(client2);
        // Each call creates a new mock instance
        assertNotSame(client1, client2);
    }
}