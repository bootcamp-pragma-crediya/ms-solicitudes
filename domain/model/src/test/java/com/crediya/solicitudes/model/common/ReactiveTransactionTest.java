package com.crediya.solicitudes.model.common;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class ReactiveTransactionTest {

    @Test
    void shouldExecuteTransactional() {
        ReactiveTransaction transaction = new ReactiveTransaction() {
            @Override
            public <T> Mono<T> transactional(Mono<T> publisher) {
                return publisher;
            }
        };
        
        Mono<String> result = transaction.transactional(Mono.just("test"));
        
        StepVerifier.create(result)
                .expectNext("test")
                .verifyComplete();
    }

    @Test
    void shouldHandleError() {
        ReactiveTransaction transaction = new ReactiveTransaction() {
            @Override
            public <T> Mono<T> transactional(Mono<T> publisher) {
                return publisher;
            }
        };
        
        Mono<String> result = transaction.transactional(Mono.error(new RuntimeException("test error")));
        
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void shouldHandleEmpty() {
        ReactiveTransaction transaction = new ReactiveTransaction() {
            @Override
            public <T> Mono<T> transactional(Mono<T> publisher) {
                return publisher;
            }
        };
        
        Mono<String> result = transaction.transactional(Mono.empty());
        
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void shouldBeAssignableToFunctionalInterface() {
        ReactiveTransaction transaction = new ReactiveTransaction() {
            @Override
            public <T> Mono<T> transactional(Mono<T> publisher) {
                return publisher;
            }
        };
        
        assertNotNull(transaction);
        
        Mono<String> result = transaction.transactional(Mono.just("hello"));
        
        StepVerifier.create(result)
                .expectNext("hello")
                .verifyComplete();
    }
}