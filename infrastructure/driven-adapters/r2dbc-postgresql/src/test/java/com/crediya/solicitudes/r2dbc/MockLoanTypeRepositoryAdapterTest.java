package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class MockLoanTypeRepositoryAdapterTest {

    private MockLoanTypeRepositoryAdapter adapter;
    
    @BeforeEach
    void setUp() {
        adapter = new MockLoanTypeRepositoryAdapter();
    }
    
    @Test
    void shouldReturnTrueForValidLoanType() {
        StepVerifier.create(adapter.existsActiveByCode("PERSONAL"))
                .expectNext(true)
                .verifyComplete();
    }
    
    @Test
    void shouldReturnTrueForMortgageLoanType() {
        StepVerifier.create(adapter.existsActiveByCode("MORTGAGE"))
                .expectNext(true)
                .verifyComplete();
    }
    
    @Test
    void shouldReturnFalseForInvalidLoanType() {
        StepVerifier.create(adapter.existsActiveByCode("INVALID"))
                .expectNext(false)
                .verifyComplete();
    }
}