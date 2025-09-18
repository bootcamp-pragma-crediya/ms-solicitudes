package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
@ConditionalOnProperty(name = "app.mock-mode", havingValue = "true")
public class MockLoanApplicationRepositoryAdapter implements com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository {

    @Override
    public Mono<LoanApplication> save(LoanApplication application) {
        return Mono.just(application.toBuilder()
                .id(UUID.randomUUID().toString())
                .build());
    }

    @Override
    public Flux<LoanApplication> findByStatusIn(List<LoanStatus> statuses, int page, int size) {
        log.info("[MOCK] Using mock data for statuses={} page={} size={}", statuses, page, size);
        return Flux.fromIterable(List.of(
            LoanApplication.builder()
                .id("1")
                .customerDocument("12345678")
                .email("juan@example.com")
                .customerName("Juan Pérez")
                .amount(new BigDecimal("50000"))
                .termMonths(24)
                .loanType("PERSONAL")
                .interestRate(new BigDecimal("12.5"))
                .baseSalary(new BigDecimal("3000000"))
                .monthlyPayment(new BigDecimal("2500"))
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build(),
            LoanApplication.builder()
                .id("2")
                .customerDocument("87654321")
                .email("maria@example.com")
                .customerName("María García")
                .amount(new BigDecimal("100000"))
                .termMonths(36)
                .loanType("HIPOTECARIO")
                .interestRate(new BigDecimal("8.5"))
                .baseSalary(new BigDecimal("5000000"))
                .monthlyPayment(new BigDecimal("3200"))
                .status(LoanStatus.REJECTED)
                .createdAt(OffsetDateTime.now().minusDays(1))
                .build()
        )).skip(page * size).take(size);
    }

    @Override
    public Mono<Long> countByStatusIn(List<LoanStatus> statuses) {
        return Mono.just(2L);
    }
}