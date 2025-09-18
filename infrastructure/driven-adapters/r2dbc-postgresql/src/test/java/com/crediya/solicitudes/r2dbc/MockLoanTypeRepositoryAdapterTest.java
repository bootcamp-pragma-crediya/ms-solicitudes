package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MockLoanTypeRepositoryAdapterTest {

    private final MockLoanTypeRepositoryAdapter repository = new MockLoanTypeRepositoryAdapter();

    @Test
    void shouldReturnTrueForValidLoanTypes() {
        Mono<Boolean> personalResult = repository.existsActiveByCode("PERSONAL");
        Mono<Boolean> mortgageResult = repository.existsActiveByCode("MORTGAGE");
        Mono<Boolean> autoResult = repository.existsActiveByCode("AUTO");
        Mono<Boolean> businessResult = repository.existsActiveByCode("BUSINESS");

        StepVerifier.create(personalResult)
                .expectNext(true)
                .verifyComplete();

        StepVerifier.create(mortgageResult)
                .expectNext(true)
                .verifyComplete();

        StepVerifier.create(autoResult)
                .expectNext(true)
                .verifyComplete();

        StepVerifier.create(businessResult)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldReturnFalseForInvalidLoanTypes() {
        Mono<Boolean> invalidResult = repository.existsActiveByCode("INVALID");
        Mono<Boolean> emptyResult = repository.existsActiveByCode("");
        Mono<Boolean> nullResult = repository.existsActiveByCode(null);

        StepVerifier.create(invalidResult)
                .expectNext(false)
                .verifyComplete();

        StepVerifier.create(emptyResult)
                .expectNext(false)
                .verifyComplete();

        StepVerifier.create(nullResult)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void shouldBeCaseExact() {
        Mono<Boolean> lowercaseResult = repository.existsActiveByCode("personal");
        Mono<Boolean> mixedCaseResult = repository.existsActiveByCode("Personal");

        StepVerifier.create(lowercaseResult)
                .expectNext(false)
                .verifyComplete();

        StepVerifier.create(mixedCaseResult)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void shouldHandleWhitespace() {
        Mono<Boolean> spacesResult = repository.existsActiveByCode(" PERSONAL ");
        Mono<Boolean> tabResult = repository.existsActiveByCode("PERSONAL\t");

        StepVerifier.create(spacesResult)
                .expectNext(false)
                .verifyComplete();

        StepVerifier.create(tabResult)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void shouldHandleSpecialCharacters() {
        Mono<Boolean> specialResult = repository.existsActiveByCode("PERSONAL@");
        Mono<Boolean> numberResult = repository.existsActiveByCode("PERSONAL1");

        StepVerifier.create(specialResult)
                .expectNext(false)
                .verifyComplete();

        StepVerifier.create(numberResult)
                .expectNext(false)
                .verifyComplete();
    }
}