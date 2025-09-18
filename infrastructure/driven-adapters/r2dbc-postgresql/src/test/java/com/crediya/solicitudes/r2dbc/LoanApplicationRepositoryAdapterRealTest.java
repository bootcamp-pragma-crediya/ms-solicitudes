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

class LoanApplicationRepositoryAdapterRealTest {

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
    void shouldSaveLoanApplication() {
        LoanApplication app = LoanApplication.builder()
                .id("1")
                .userId("user1")
                .customerDocument("12345678")
                .email("test@email.com")
                .customerName("Test User")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .interestRate(BigDecimal.valueOf(5.5))
                .baseSalary(BigDecimal.valueOf(3000000))
                .monthlyPayment(BigDecimal.valueOf(900))
                .status(LoanStatus.PENDING_REVIEW)
                .build();

        LoanApplicationData savedData = LoanApplicationData.builder()
                .id("1")
                .userId("user1")
                .customerDocument("12345678")
                .email("test@email.com")
                .customerName("Test User")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .interestRate(BigDecimal.valueOf(5.5))
                .baseSalary(BigDecimal.valueOf(3000000))
                .monthlyPayment(BigDecimal.valueOf(900))
                .status("PENDING_REVIEW")
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.save(any(LoanApplicationData.class))).thenReturn(Mono.just(savedData));

        StepVerifier.create(adapter.save(app))
                .expectNextMatches(result -> 
                    "1".equals(result.getId()) &&
                    "user1".equals(result.getUserId()) &&
                    "Test User".equals(result.getCustomerName()) &&
                    BigDecimal.valueOf(3000000).equals(result.getBaseSalary()))
                .verifyComplete();
    }

    @Test
    void shouldFindByStatusIn() {
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW);
        
        LoanApplicationData data = LoanApplicationData.builder()
                .id("1")
                .userId("user1")
                .customerDocument("12345678")
                .email("test@email.com")
                .customerName("Test User")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status("PENDING_REVIEW")
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.findByStatusInOrderByCreatedAtDesc(anyList(), anyInt(), anyInt()))
                .thenReturn(Flux.just(data));

        StepVerifier.create(adapter.findByStatusIn(statuses, 0, 10))
                .expectNextMatches(result -> 
                    "1".equals(result.getId()) &&
                    LoanStatus.PENDING_REVIEW.equals(result.getStatus()))
                .verifyComplete();
    }

    @Test
    void shouldCountByStatusIn() {
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW);
        
        when(repository.countByStatusIn(anyList())).thenReturn(Mono.just(5L));

        StepVerifier.create(adapter.countByStatusIn(statuses))
                .expectNext(5L)
                .verifyComplete();
    }
}