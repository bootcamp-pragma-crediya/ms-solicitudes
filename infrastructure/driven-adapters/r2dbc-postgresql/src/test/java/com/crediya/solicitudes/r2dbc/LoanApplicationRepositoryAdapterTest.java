package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanApplicationRepositoryAdapterTest {

    @Mock
    private LoanApplicationRepository repository;
    
    @Mock
    private ObjectMapper mapper;
    
    private LoanApplicationRepositoryAdapter adapter;
    
    @BeforeEach
    void setUp() {
        adapter = new LoanApplicationRepositoryAdapter(repository, mapper);
    }
    
    @Test
    void shouldSaveLoanApplication() {
        // Given
        LoanApplication app = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .build();
                
        LoanApplicationData data = new LoanApplicationData();
        data.setId("1");
        data.setStatus("PENDING_REVIEW");
        
        when(mapper.map(any(LoanApplication.class), eq(LoanApplicationData.class))).thenReturn(data);
        when(repository.save(any(LoanApplicationData.class))).thenReturn(Mono.just(data));
        when(mapper.map(any(LoanApplicationData.class), eq(LoanApplication.class))).thenReturn(app);
        
        // When & Then
        StepVerifier.create(adapter.save(app))
                .expectNextMatches(saved -> saved.getStatus() == LoanStatus.PENDING_REVIEW)
                .verifyComplete();
    }
    
    @Test
    void shouldFindByStatusIn() {
        // Given
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW, LoanStatus.MANUAL_REVIEW);
        
        LoanApplicationData data = new LoanApplicationData();
        data.setId("1");
        data.setStatus("PENDING_REVIEW");
        
        LoanApplication app = LoanApplication.builder()
                .id("1")
                .status(LoanStatus.PENDING_REVIEW)
                .build();
        
        when(repository.findByStatusInOrderByCreatedAtDesc(anyList(), anyInt(), anyInt()))
                .thenReturn(Flux.just(data));
        when(mapper.map(any(LoanApplicationData.class), eq(LoanApplication.class))).thenReturn(app);
        
        // When & Then
        StepVerifier.create(adapter.findByStatusIn(statuses, 0, 10))
                .expectNextMatches(result -> result.getStatus() == LoanStatus.PENDING_REVIEW)
                .verifyComplete();
    }
    
    @Test
    void shouldCountByStatusIn() {
        // Given
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW);
        when(repository.countByStatusIn(anyList())).thenReturn(Mono.just(5L));
        
        // When & Then
        StepVerifier.create(adapter.countByStatusIn(statuses))
                .expectNext(5L)
                .verifyComplete();
    }
}