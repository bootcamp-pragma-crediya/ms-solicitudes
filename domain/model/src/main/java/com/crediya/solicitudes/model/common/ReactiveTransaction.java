package com.crediya.solicitudes.model.common;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ReactiveTransaction {
    <T> Mono<T> transactional(Mono<T> publisher);
}