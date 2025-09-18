package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class LoanApplicationRepositoryAdapterCoverageTest {

    @Mock
    private LoanApplicationRepository repository;
    
    @Mock
    private ObjectMapper mapper;
    
    private LoanApplicationRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new LoanApplicationRepositoryAdapter(repository, mapper);
    }

    @Test
    void shouldSaveWithNullCreatedAt() {
        LoanApplication app = LoanApplication.builder()
                .userId("user1")
                .customerDocument("12345678")
                .customerName("Test User")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .build();

        LoanApplicationData savedData = LoanApplicationData.builder()
                .id("1")
                .userId("user1")
                .customerDocument("12345678")
                .customerName("Test User")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status("PENDING_REVIEW")
                .createdAt(null)
                .build();

        when(repository.save(any(LoanApplicationData.class)))
                .thenReturn(Mono.just(savedData));

        StepVerifier.create(adapter.save(app))
                .expectNextMatches(result -> result.getId().equals("1"))
                .verifyComplete();
    }

    @Test
    void shouldSaveWithCreatedAt() {
        LoanApplication app = LoanApplication.builder()
                .userId("user1")
                .customerDocument("12345678")
                .customerName("Test User")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .build();

        LoanApplicationData savedData = LoanApplicationData.builder()
                .id("1")
                .userId("user1")
                .customerDocument("12345678")
                .customerName("Test User")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status("PENDING_REVIEW")
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.save(any(LoanApplicationData.class)))
                .thenReturn(Mono.just(savedData));

        StepVerifier.create(adapter.save(app))
                .expectNextMatches(result -> result.getId().equals("1"))
                .verifyComplete();
    }

    @Test
    void shouldFindByStatusInWithCreatedAt() {
        LoanApplicationData data = LoanApplicationData.builder()
                .id("1")
                .userId("user1")
                .customerDocument("12345678")
                .customerName("Test User")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status("PENDING_REVIEW")
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.findByStatusInOrderByCreatedAtDesc(anyList(), anyInt(), anyInt()))
                .thenReturn(Flux.just(data));

        StepVerifier.create(adapter.findByStatusIn(List.of(LoanStatus.PENDING_REVIEW), 0, 10))
                .expectNextMatches(result -> result.getId().equals("1"))
                .verifyComplete();
    }

    @Test
    void shouldCountByStatusIn() {
        when(repository.countByStatusIn(anyList()))
                .thenReturn(Mono.just(5L));

        StepVerifier.create(adapter.countByStatusIn(List.of(LoanStatus.PENDING_REVIEW)))
                .expectNext(5L)
                .verifyComplete();
    }
}