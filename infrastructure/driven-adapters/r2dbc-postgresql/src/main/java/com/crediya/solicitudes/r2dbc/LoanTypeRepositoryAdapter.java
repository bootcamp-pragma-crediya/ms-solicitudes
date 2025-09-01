package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loantype.gateways.LoanTypeRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@ConditionalOnProperty(name = "app.mock-mode", havingValue = "false")
public class LoanTypeRepositoryAdapter implements LoanTypeRepository {

    private final LoanTypeR2dbcRepository repository;

    public LoanTypeRepositoryAdapter(LoanTypeR2dbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Boolean> existsActiveByCode(String code) {
        return repository.existsByCodeAndActiveTrue(code);
    }
}