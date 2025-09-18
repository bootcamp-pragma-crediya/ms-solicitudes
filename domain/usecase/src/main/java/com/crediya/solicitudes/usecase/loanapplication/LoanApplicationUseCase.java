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
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class LoanApplicationUseCase {
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final ReactiveTransaction tx;

    public Mono<LoanApplication> execute(LoanApplication application, String userId, String userEmail) {
        return Mono.justOrEmpty(application)
                .switchIfEmpty(Mono.error(new InvalidLoanApplicationException(ValidationMessage.BODY_REQUIRED.getMessage())))
                .flatMap(this::validateAmount)
                .flatMap(this::validateTermMonths)
                .flatMap(this::validateLoanType)
                .flatMap(this::validateLoanTypeExists)
                .flatMap(app -> enrichWithUserData(app, userId, userEmail))
                .flatMap(this::calculateLoanTerms)
                .flatMap(this::persistWithInitialStatus)
                .transform(tx::transactional);
    }

    private Mono<LoanApplication> enrichWithUserData(LoanApplication application, String userId, String userEmail) {
        return Mono.just(application.toBuilder()
                .userId(userId)
                .email(userEmail)
                .build());
    }
    
    private Mono<LoanApplication> calculateLoanTerms(LoanApplication application) {
        BigDecimal interestRate = calculateInterestRate(application.getLoanType());
        BigDecimal monthlyPayment = calculateMonthlyPayment(
                application.getAmount(), 
                interestRate, 
                application.getTermMonths()
        );
        
        return Mono.just(application.toBuilder()
                .interestRate(interestRate)
                .monthlyPayment(monthlyPayment)
                .build());
    }
    
    private BigDecimal calculateInterestRate(String loanType) {
        return switch (loanType) {
            case "PERSONAL" -> new BigDecimal("15.5");
            case "VEHICULAR" -> new BigDecimal("12.0");
            case "HIPOTECARIO" -> new BigDecimal("8.5");
            case "EDUCATIVO" -> new BigDecimal("10.0");
            default -> new BigDecimal("15.0");
        };
    }
    
    private BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal annualRate, Integer months) {
        BigDecimal monthlyRate = annualRate.divide(new BigDecimal("1200"), 10, RoundingMode.HALF_UP);
        BigDecimal factor = monthlyRate.add(BigDecimal.ONE).pow(months);
        return amount.multiply(monthlyRate).multiply(factor)
                .divide(factor.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
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
        OffsetDateTime now = OffsetDateTime.now();
        var toPersist = in.toBuilder()
                .id(UUID.randomUUID().toString())
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(now)
                .build();

        return loanApplicationRepository.save(toPersist);
    }
}
