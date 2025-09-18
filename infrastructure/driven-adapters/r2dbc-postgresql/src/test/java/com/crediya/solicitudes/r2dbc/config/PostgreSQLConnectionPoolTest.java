package com.crediya.solicitudes.r2dbc.config;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgreSQLConnectionPoolTest {

    @Test
    void shouldCreateConnectionFactory() {
        PostgreSQLConnectionPool pool = new PostgreSQLConnectionPool();
        PostgresqlConnectionProperties properties = new PostgresqlConnectionProperties(
                "localhost", 5432, "test", "public", "user", "pass"
        );
        
        ConnectionFactory factory = pool.connectionFactory(properties);
        
        assertNotNull(factory);
    }

    @Test
    void shouldUseDefaultPortWhenNull() {
        PostgreSQLConnectionPool pool = new PostgreSQLConnectionPool();
        PostgresqlConnectionProperties properties = new PostgresqlConnectionProperties(
                "localhost", null, "test", "public", "user", "pass"
        );
        
        ConnectionFactory factory = pool.connectionFactory(properties);
        
        assertNotNull(factory);
    }

    @Test
    void shouldUseDefaultSchemaWhenNull() {
        PostgreSQLConnectionPool pool = new PostgreSQLConnectionPool();
        PostgresqlConnectionProperties properties = new PostgresqlConnectionProperties(
                "localhost", 5432, "test", null, "user", "pass"
        );
        
        ConnectionFactory factory = pool.connectionFactory(properties);
        
        assertNotNull(factory);
    }

    @Test
    void shouldHaveCorrectConstants() {
        assertEquals(12, PostgreSQLConnectionPool.INITIAL_SIZE);
        assertEquals(15, PostgreSQLConnectionPool.MAX_SIZE);
        assertEquals(30, PostgreSQLConnectionPool.MAX_IDLE_TIME_MINUTES);
    }
}