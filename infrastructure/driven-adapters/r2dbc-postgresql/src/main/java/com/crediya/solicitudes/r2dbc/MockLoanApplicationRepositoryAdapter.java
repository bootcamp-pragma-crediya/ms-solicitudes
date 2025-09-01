package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "app.mock-mode", havingValue = "true", matchIfMissing = true)
public class MockLoanApplicationRepositoryAdapter implements com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository {

    @Override
    public Mono<LoanApplication> save(LoanApplication application) {
        return Mono.just(application.toBuilder()
                .id(UUID.randomUUID().toString())
                .build());
    }
}