package com.crediya.solicitudes.usecase.loanapplication;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListLoanApplicationsUseCaseTest {

    @Mock
    private LoanApplicationRepository repository;

    private ListLoanApplicationsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListLoanApplicationsUseCase(repository);
    }

    @Test
    void shouldReturnPagedApplicationsForReview() {
        // Given
        List<LoanApplication> applications = List.of(
            LoanApplication.builder()
                .id("1")
                .customerDocument("12345678")
                .email("test@example.com")
                .customerName("Test User")
                .amount(new BigDecimal("50000"))
                .termMonths(24)
                .loanType("PERSONAL")
                .interestRate(new BigDecimal("12.5"))
                .baseSalary(new BigDecimal("3000000"))
                .monthlyPayment(new BigDecimal("2500"))
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build()
        );

        when(repository.findByStatusIn(any(), anyInt(), anyInt()))
            .thenReturn(Flux.fromIterable(applications));
        when(repository.countByStatusIn(any()))
            .thenReturn(Mono.just(1L));

        // When & Then
        StepVerifier.create(useCase.execute(0, 10))
            .expectNextMatches(result -> 
                result.getContent().size() == 1 &&
                result.getTotalElements() == 1 &&
                result.getPage() == 0 &&
                result.getSize() == 10 &&
                result.getTotalPages() == 1
            )
            .verifyComplete();
    }
}