package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class MockLoanTypeRepositoryAdapterTest {

    private final MockLoanTypeRepositoryAdapter adapter = new MockLoanTypeRepositoryAdapter();

    @Test
    void shouldReturnTrueForValidLoanTypes() {
        StepVerifier.create(adapter.existsActiveByCode(com.crediya.solicitudes.model.loantype.LoanTypeCode.PERSONAL.getCode()))
                .expectNext(true)
                .verifyComplete();

        StepVerifier.create(adapter.existsActiveByCode(com.crediya.solicitudes.model.loantype.LoanTypeCode.MORTGAGE.getCode()))
                .expectNext(true)
                .verifyComplete();

        StepVerifier.create(adapter.existsActiveByCode(com.crediya.solicitudes.model.loantype.LoanTypeCode.AUTO.getCode()))
                .expectNext(true)
                .verifyComplete();

        StepVerifier.create(adapter.existsActiveByCode(com.crediya.solicitudes.model.loantype.LoanTypeCode.BUSINESS.getCode()))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldReturnFalseForInvalidLoanTypes() {
        StepVerifier.create(adapter.existsActiveByCode("INVALID"))
                .expectNext(false)
                .verifyComplete();

        StepVerifier.create(adapter.existsActiveByCode("UNKNOWN"))
                .expectNext(false)
                .verifyComplete();
    }
}