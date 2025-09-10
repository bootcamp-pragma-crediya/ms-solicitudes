package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanTypeRepositoryAdapterTest {

    @Mock
    private LoanTypeR2dbcRepository repository;
    
    private LoanTypeRepositoryAdapter adapter;
    
    @BeforeEach
    void setUp() {
        adapter = new LoanTypeRepositoryAdapter(repository);
    }
    
    @Test
    void shouldReturnTrueWhenLoanTypeExists() {
        // Given
        String code = "PERSONAL";
        when(repository.existsByCodeAndActiveTrue(code)).thenReturn(Mono.just(true));
        
        // When & Then
        StepVerifier.create(adapter.existsActiveByCode(code))
                .expectNext(true)
                .verifyComplete();
    }
    
    @Test
    void shouldReturnFalseWhenLoanTypeDoesNotExist() {
        // Given
        String code = "INVALID";
        when(repository.existsByCodeAndActiveTrue(code)).thenReturn(Mono.just(false));
        
        // When & Then
        StepVerifier.create(adapter.existsActiveByCode(code))
                .expectNext(false)
                .verifyComplete();
    }
}