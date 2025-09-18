package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MockLoanApplicationRepositoryAdapterTest {

    private final MockLoanApplicationRepositoryAdapter repository = new MockLoanApplicationRepositoryAdapter();

    @Test
    void shouldSaveLoanApplication() {
        LoanApplication application = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build();

        Mono<LoanApplication> result = repository.save(application);

        StepVerifier.create(result)
                .assertNext(saved -> {
                    assertNotNull(saved.getId());
                    assertEquals("12345678", saved.getCustomerDocument());
                    assertEquals(BigDecimal.valueOf(10000), saved.getAmount());
                    assertEquals(12, saved.getTermMonths());
                    assertEquals("PERSONAL", saved.getLoanType());
                    assertEquals(LoanStatus.PENDING_REVIEW, saved.getStatus());
                })
                .verifyComplete();
    }

    @Test
    void shouldFindByStatusIn() {
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW, LoanStatus.REJECTED);

        Flux<LoanApplication> result = repository.findByStatusIn(statuses, 0, 10);

        StepVerifier.create(result)
                .assertNext(app -> {
                    assertEquals("1", app.getId());
                    assertEquals("12345678", app.getCustomerDocument());
                    assertEquals("juan@example.com", app.getEmail());
                    assertEquals("Juan Pérez", app.getCustomerName());
                    assertEquals(LoanStatus.PENDING_REVIEW, app.getStatus());
                })
                .assertNext(app -> {
                    assertEquals("2", app.getId());
                    assertEquals("87654321", app.getCustomerDocument());
                    assertEquals("maria@example.com", app.getEmail());
                    assertEquals("María García", app.getCustomerName());
                    assertEquals(LoanStatus.REJECTED, app.getStatus());
                })
                .verifyComplete();
    }

    @Test
    void shouldFindByStatusInWithPagination() {
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW);

        Flux<LoanApplication> result = repository.findByStatusIn(statuses, 1, 1);

        StepVerifier.create(result)
                .assertNext(app -> {
                    assertEquals("2", app.getId());
                    assertEquals("María García", app.getCustomerName());
                })
                .verifyComplete();
    }

    @Test
    void shouldCountByStatusIn() {
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW, LoanStatus.APPROVED);

        Mono<Long> result = repository.countByStatusIn(statuses);

        StepVerifier.create(result)
                .expectNext(2L)
                .verifyComplete();
    }

    @Test
    void shouldHandleEmptyStatusList() {
        List<LoanStatus> statuses = List.of();

        Flux<LoanApplication> result = repository.findByStatusIn(statuses, 0, 10);

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldHandleLargePage() {
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW);

        Flux<LoanApplication> result = repository.findByStatusIn(statuses, 10, 10);

        StepVerifier.create(result)
                .verifyComplete();
    }
}