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

        if (application == null) {
            return Mono.error(new InvalidLoanApplicationException("Body is required"));
        }
        if (application.getCustomerDocument() == null || application.getCustomerDocument().isBlank()) {
            return Mono.error(new InvalidLoanApplicationException("customerDocument is required"));
        }
        if (application.getAmount() == null || application.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new InvalidLoanApplicationException("amount must be > 0"));
        }
        if (application.getTermMonths() == null || application.getTermMonths() <= 0) {
            return Mono.error(new InvalidLoanApplicationException("termMonths must be > 0"));
        }
        if (application.getLoanType() == null || application.getLoanType().isBlank()) {
            return Mono.error(new InvalidLoanApplicationException("loanType is required"));
        }

        return loanTypeRepository.existsActiveByCode(application.getLoanType())
                .flatMap(exists -> exists
                        ? persistWithInitialStatus(application)
                        : Mono.error(new InvalidLoanTypeException("loanType is invalid or inactive"))
                )
                .transform(tx::transactional);
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
