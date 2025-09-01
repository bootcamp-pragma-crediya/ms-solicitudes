package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loantype.gateways.LoanTypeRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Set;

@Repository
@ConditionalOnProperty(name = "app.mock-mode", havingValue = "true", matchIfMissing = true)
public class MockLoanTypeRepositoryAdapter implements LoanTypeRepository {

    private final Set<String> validLoanTypes = Set.of("PERSONAL", "MORTGAGE", "AUTO", "BUSINESS");

    @Override
    public Mono<Boolean> existsActiveByCode(String code) {
        return Mono.just(validLoanTypes.contains(code));
    }
}