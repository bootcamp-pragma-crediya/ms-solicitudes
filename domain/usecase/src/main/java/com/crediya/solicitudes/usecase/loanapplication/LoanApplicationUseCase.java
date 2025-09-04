package com.crediya.solicitudes.usecase.loanapplication;

import com.crediya.solicitudes.model.common.ReactiveTransaction;
import com.crediya.solicitudes.model.exception.InvalidLoanApplicationException;
import com.crediya.solicitudes.model.exception.InvalidLoanTypeException;
import com.crediya.solicitudes.model.exception.ValidationMessage;
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
                .switchIfEmpty(Mono.error(new InvalidLoanApplicationException(ValidationMessage.BODY_REQUIRED.getMessage())))
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
                .switchIfEmpty(Mono.error(new InvalidLoanApplicationException(ValidationMessage.CUSTOMER_DOCUMENT_REQUIRED.getMessage())));
    }

    private Mono<LoanApplication> validateAmount(LoanApplication application) {
        return Mono.just(application)
                .filter(app -> app.getAmount() != null && app.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .switchIfEmpty(Mono.error(new InvalidLoanApplicationException(ValidationMessage.AMOUNT_MUST_BE_POSITIVE.getMessage())));
    }

    private Mono<LoanApplication> validateTermMonths(LoanApplication application) {
        return Mono.just(application)
                .filter(app -> app.getTermMonths() != null && app.getTermMonths() > 0)
                .switchIfEmpty(Mono.error(new InvalidLoanApplicationException(ValidationMessage.TERM_MONTHS_MUST_BE_POSITIVE.getMessage())));
    }

    private Mono<LoanApplication> validateLoanType(LoanApplication application) {
        return Mono.just(application)
                .filter(app -> app.getLoanType() != null && !app.getLoanType().isBlank())
                .switchIfEmpty(Mono.error(new InvalidLoanApplicationException(ValidationMessage.LOAN_TYPE_REQUIRED.getMessage())));
    }

    private Mono<LoanApplication> validateLoanTypeExists(LoanApplication application) {
        return loanTypeRepository.existsActiveByCode(application.getLoanType())
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new InvalidLoanTypeException(ValidationMessage.LOAN_TYPE_INVALID.getMessage())))
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
