package com.crediya.solicitudes.usecase.loanapplication;

import com.crediya.solicitudes.model.common.ReactiveTransaction;
import com.crediya.solicitudes.model.exception.InvalidLoanApplicationException;
import com.crediya.solicitudes.model.exception.InvalidLoanTypeException;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.model.loantype.gateways.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@RequiredArgsConstructor
public class LoanApplicationUseCase {
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final ReactiveTransaction tx;

    public Mono<LoanApplication> execute(LoanApplication application) {
        return Mono.justOrEmpty(application)
                .switchIfEmpty(Mono.error(new InvalidLoanApplicationException("Body is required")))
                .flatMap(this::validateCustomerDocument)
                .flatMap(this::validateAmount)
                .flatMap(this::validateTermMonths)
                .flatMap(this::validateLoanType)
                .flatMap(this::validateLoanTypeExists)
                .flatMap(this::persistWithInitialStatus)
                .transform(tx::transactional);
    }

    private Mono<LoanApplication> validateCustomerDocument(LoanApplication application) {
        return Mono.just(application)
                .filter(app -> app.getCustomerDocument() != null && !app.getCustomerDocument().isBlank())
                .switchIfEmpty(Mono.error(new InvalidLoanApplicationException("customerDocument is required")));
    }

    private Mono<LoanApplication> validateAmount(LoanApplication application) {
        return Mono.just(application)
                .filter(app -> app.getAmount() != null && app.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .switchIfEmpty(Mono.error(new InvalidLoanApplicationException("amount must be > 0")));
    }

    private Mono<LoanApplication> validateTermMonths(LoanApplication application) {
        return Mono.just(application)
                .filter(app -> app.getTermMonths() != null && app.getTermMonths() > 0)
                .switchIfEmpty(Mono.error(new InvalidLoanApplicationException("termMonths must be > 0")));
    }

    private Mono<LoanApplication> validateLoanType(LoanApplication application) {
        return Mono.just(application)
                .filter(app -> app.getLoanType() != null && !app.getLoanType().isBlank())
                .switchIfEmpty(Mono.error(new InvalidLoanApplicationException("loanType is required")));
    }

    private Mono<LoanApplication> validateLoanTypeExists(LoanApplication application) {
        return loanTypeRepository.existsActiveByCode(application.getLoanType())
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new InvalidLoanTypeException("loanType is invalid or inactive")))
                .thenReturn(application);
    }

    private Mono<LoanApplication> persistWithInitialStatus(LoanApplication in) {
        var toPersist = in.toBuilder()
                .id(null)
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build();

        return loanApplicationRepository.save(toPersist);
    }
}
