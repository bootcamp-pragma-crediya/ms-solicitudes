package com.crediya.solicitudes.r2dbc.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostgresqlConnectionPropertiesTest {

    @Test
    void shouldCreatePropertiesWithAllFields() {
        // Given
        String host = "localhost";
        Integer port = 5432;
        String database = "testdb";
        String schema = "public";
        String username = "user";
        String password = "pass";
        
        // When
        PostgresqlConnectionProperties properties = new PostgresqlConnectionProperties(
                host, port, database, schema, username, password);
        
        // Then
        assertThat(properties.host()).isEqualTo(host);
        assertThat(properties.port()).isEqualTo(port);
        assertThat(properties.database()).isEqualTo(database);
        assertThat(properties.schema()).isEqualTo(schema);
        assertThat(properties.username()).isEqualTo(username);
        assertThat(properties.password()).isEqualTo(password);
    }
    
    @Test
    void shouldSupportEqualsAndHashCode() {
        // Given
        PostgresqlConnectionProperties props1 = new PostgresqlConnectionProperties(
                "localhost", 5432, "testdb", "public", "user", "pass");
        PostgresqlConnectionProperties props2 = new PostgresqlConnectionProperties(
                "localhost", 5432, "testdb", "public", "user", "pass");
        
        // Then
        assertThat(props1).isEqualTo(props2);
        assertThat(props1.hashCode()).isEqualTo(props2.hashCode());
    }
    
    @Test
    void shouldSupportToString() {
        // Given
        PostgresqlConnectionProperties properties = new PostgresqlConnectionProperties(
                "localhost", 5432, "testdb", "public", "user", "pass");
        
        // When
        String result = properties.toString();
        
        // Then
        assertThat(result).contains("localhost");
        assertThat(result).contains("5432");
        assertThat(result).contains("testdb");
        assertThat(result).contains("public");
        assertThat(result).contains("user");
    }
}