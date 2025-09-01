package com.crediya.solicitudes.model.loantype.gateways;

import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Mono<Boolean> existsActiveByCode(String code);
}