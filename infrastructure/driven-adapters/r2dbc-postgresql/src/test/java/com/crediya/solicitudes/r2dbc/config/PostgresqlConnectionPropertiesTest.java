package com.crediya.solicitudes.r2dbc.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresqlConnectionPropertiesTest {

    @Test
    void shouldCreateConnectionProperties() {
        PostgresqlConnectionProperties properties = new PostgresqlConnectionProperties(
                "localhost", 5432, "testdb", "public", "user", "password"
        );

        assertEquals("localhost", properties.host());
        assertEquals(5432, properties.port());
        assertEquals("testdb", properties.database());
        assertEquals("public", properties.schema());
        assertEquals("user", properties.username());
        assertEquals("password", properties.password());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        PostgresqlConnectionProperties props1 = new PostgresqlConnectionProperties(
                "localhost", 5432, "testdb", "public", "user", "password"
        );
        PostgresqlConnectionProperties props2 = new PostgresqlConnectionProperties(
                "localhost", 5432, "testdb", "public", "user", "password"
        );

        assertEquals(props1, props2);
        assertEquals(props1.hashCode(), props2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        PostgresqlConnectionProperties properties = new PostgresqlConnectionProperties(
                "localhost", 5432, "testdb", "public", "user", "password"
        );

        String toString = properties.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("localhost"));
        assertTrue(toString.contains("5432"));
        assertTrue(toString.contains("testdb"));
    }

    @Test
    void shouldHandleNullValues() {
        PostgresqlConnectionProperties properties = new PostgresqlConnectionProperties(
                null, null, null, null, null, null
        );

        assertNull(properties.host());
        assertNull(properties.port());
        assertNull(properties.database());
        assertNull(properties.schema());
        assertNull(properties.username());
        assertNull(properties.password());
    }

    @Test
    void shouldCreateWithDifferentPorts() {
        PostgresqlConnectionProperties props1 = new PostgresqlConnectionProperties(
                "localhost", 5432, "db", "schema", "user", "pass"
        );
        PostgresqlConnectionProperties props2 = new PostgresqlConnectionProperties(
                "localhost", 3306, "db", "schema", "user", "pass"
        );

        assertNotEquals(props1, props2);
        assertEquals(5432, props1.port());
        assertEquals(3306, props2.port());
    }
}