package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class LoanTypeRepositoryAdapterRealTest {

    @Mock
    private LoanTypeR2dbcRepository repository;
    
    private LoanTypeRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new LoanTypeRepositoryAdapter(repository);
    }

    @Test
    void shouldReturnTrueWhenLoanTypeExistsAndActive() {
        when(repository.existsByCodeAndActiveTrue("PERSONAL")).thenReturn(Mono.just(true));

        StepVerifier.create(adapter.existsActiveByCode("PERSONAL"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldReturnFalseWhenLoanTypeDoesNotExist() {
        when(repository.existsByCodeAndActiveTrue("INVALID")).thenReturn(Mono.just(false));

        StepVerifier.create(adapter.existsActiveByCode("INVALID"))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void shouldHandleError() {
        when(repository.existsByCodeAndActiveTrue(anyString()))
                .thenReturn(Mono.error(new RuntimeException("Database error")));

        StepVerifier.create(adapter.existsActiveByCode("PERSONAL"))
                .expectError(RuntimeException.class)
                .verify();
    }
}