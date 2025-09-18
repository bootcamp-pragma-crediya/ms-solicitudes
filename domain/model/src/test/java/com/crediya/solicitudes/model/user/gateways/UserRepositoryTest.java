package com.crediya.solicitudes.model.user.gateways;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {

    @Test
    void shouldCreateUserDataRecord() {
        // Given
        String id = "user123";
        String name = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        BigDecimal baseSalary = new BigDecimal("50000");

        // When
        UserRepository.UserData userData = new UserRepository.UserData(
            id, name, lastName, email, baseSalary
        );

        // Then
        assertThat(userData.id()).isEqualTo(id);
        assertThat(userData.name()).isEqualTo(name);
        assertThat(userData.lastName()).isEqualTo(lastName);
        assertThat(userData.email()).isEqualTo(email);
        assertThat(userData.baseSalary()).isEqualTo(baseSalary);
    }

    @Test
    void shouldHandleNullValues() {
        // When
        UserRepository.UserData userData = new UserRepository.UserData(
            null, null, null, null, null
        );

        // Then
        assertThat(userData.id()).isNull();
        assertThat(userData.name()).isNull();
        assertThat(userData.lastName()).isNull();
        assertThat(userData.email()).isNull();
        assertThat(userData.baseSalary()).isNull();
    }

    @Test
    void shouldSupportEquality() {
        // Given
        BigDecimal salary = new BigDecimal("50000");
        UserRepository.UserData userData1 = new UserRepository.UserData(
            "user123", "John", "Doe", "john@example.com", salary
        );
        UserRepository.UserData userData2 = new UserRepository.UserData(
            "user123", "John", "Doe", "john@example.com", salary
        );

        // Then
        assertThat(userData1).isEqualTo(userData2);
        assertThat(userData1.hashCode()).isEqualTo(userData2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        // Given
        UserRepository.UserData userData = new UserRepository.UserData(
            "user123", "John", "Doe", "john@example.com", new BigDecimal("50000")
        );

        // When
        String toString = userData.toString();

        // Then
        assertThat(toString).contains("user123");
        assertThat(toString).contains("John");
        assertThat(toString).contains("Doe");
        assertThat(toString).contains("john@example.com");
        assertThat(toString).contains("50000");
    }
}