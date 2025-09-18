package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class MockLoanTypeRepositoryAdapterTest {

    @InjectMocks
    private MockLoanTypeRepositoryAdapter adapter;

    @Test
    void existsActiveByCode_WithValidCode_ShouldReturnTrue() {
        StepVerifier.create(adapter.existsActiveByCode("PERSONAL"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void existsActiveByCode_WithInvalidCode_ShouldReturnFalse() {
        StepVerifier.create(adapter.existsActiveByCode("INVALID"))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void existsActiveByCode_WithMortgageCode_ShouldReturnTrue() {
        StepVerifier.create(adapter.existsActiveByCode("MORTGAGE"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void existsActiveByCode_WithAutoCode_ShouldReturnTrue() {
        StepVerifier.create(adapter.existsActiveByCode("AUTO"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void existsActiveByCode_WithNullCode_ShouldReturnFalse() {
        StepVerifier.create(adapter.existsActiveByCode(null))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void existsActiveByCode_WithEmptyCode_ShouldReturnFalse() {
        StepVerifier.create(adapter.existsActiveByCode(""))
                .expectNext(false)
                .verifyComplete();
    }
}