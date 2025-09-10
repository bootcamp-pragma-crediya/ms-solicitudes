package com.crediya.solicitudes.r2dbc.config;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostgreSQLConnectionPoolTest {

    @Test
    void shouldCreateConnectionFactory() {
        // Given
        PostgreSQLConnectionPool config = new PostgreSQLConnectionPool();
        PostgresqlConnectionProperties properties = new PostgresqlConnectionProperties(
                "localhost", 5432, "testdb", "public", "user", "pass");
        
        // When
        ConnectionFactory factory = config.connectionFactory(properties);
        
        // Then
        assertThat(factory).isNotNull();
    }
    
    @Test
    void shouldCreateConnectionFactoryWithDefaults() {
        // Given
        PostgreSQLConnectionPool config = new PostgreSQLConnectionPool();
        PostgresqlConnectionProperties properties = new PostgresqlConnectionProperties(
                "localhost", null, "testdb", null, "user", "pass");
        
        // When
        ConnectionFactory factory = config.connectionFactory(properties);
        
        // Then
        assertThat(factory).isNotNull();
    }
    
    @Test
    void shouldHaveCorrectConstants() {
        assertThat(PostgreSQLConnectionPool.INITIAL_SIZE).isEqualTo(12);
        assertThat(PostgreSQLConnectionPool.MAX_SIZE).isEqualTo(15);
        assertThat(PostgreSQLConnectionPool.MAX_IDLE_TIME_MINUTES).isEqualTo(30);
    }
}