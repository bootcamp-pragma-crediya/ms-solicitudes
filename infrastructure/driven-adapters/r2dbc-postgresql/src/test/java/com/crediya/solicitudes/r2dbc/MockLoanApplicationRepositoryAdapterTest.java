package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MockLoanApplicationRepositoryAdapterTest {

    private MockLoanApplicationRepositoryAdapter adapter;
    
    @BeforeEach
    void setUp() {
        adapter = new MockLoanApplicationRepositoryAdapter();
    }
    
    @Test
    void shouldSaveLoanApplication() {
        // Given
        LoanApplication app = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .build();
        
        // When & Then
        StepVerifier.create(adapter.save(app))
                .assertNext(saved -> {
                    assertThat(saved.getId()).isNotNull();
                    assertThat(saved.getCustomerDocument()).isEqualTo("12345678");
                    assertThat(saved.getAmount()).isEqualTo(BigDecimal.valueOf(10000));
                })
                .verifyComplete();
    }
    
    @Test
    void shouldFindByStatusIn() {
        // Given
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW, LoanStatus.REJECTED);
        
        // When & Then
        StepVerifier.create(adapter.findByStatusIn(statuses, 0, 10))
                .expectNextCount(2)
                .verifyComplete();
    }
    
    @Test
    void shouldFindByStatusInWithPagination() {
        // Given
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW);
        
        // When & Then
        StepVerifier.create(adapter.findByStatusIn(statuses, 0, 1))
                .expectNextCount(1)
                .verifyComplete();
    }
    
    @Test
    void shouldCountByStatusIn() {
        // Given
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW);
        
        // When & Then
        StepVerifier.create(adapter.countByStatusIn(statuses))
                .expectNext(2L)
                .verifyComplete();
    }
}