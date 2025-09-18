package com.crediya.solicitudes.usecase.loanapplication;

import com.crediya.solicitudes.model.common.ReactiveTransaction;
import com.crediya.solicitudes.model.exception.InvalidLoanApplicationException;
import com.crediya.solicitudes.model.exception.InvalidLoanTypeException;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.model.loantype.gateways.LoanTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanApplicationUseCaseTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;
    
    @Mock
    private LoanTypeRepository loanTypeRepository;
    
    @Mock
    private ReactiveTransaction tx;

    private LoanApplicationUseCase useCase;
    private static final String TEST_USER_ID = "test-user-id";

    @BeforeEach
    void setUp() {
        useCase = new LoanApplicationUseCase(loanApplicationRepository, loanTypeRepository, tx);
    }

    @Test
    void shouldCreateLoanApplicationSuccessfully() {
        // Given
        var input = LoanApplication.builder()
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .build();

        var saved = input.toBuilder()
                .id("generated-id")
                .customerDocument("1234567890")
                .email("test@email.com")
                .customerName("Test User")
                .baseSalary(new BigDecimal("5000000"))
                .status(LoanStatus.PENDING_REVIEW)
                .build();

        when(loanTypeRepository.existsActiveByCode("PERSONAL")).thenReturn(Mono.just(true));
        when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.just(saved));
        when(tx.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When & Then
        StepVerifier.create(useCase.execute(input, TEST_USER_ID))
                .expectNextMatches(result -> 
                    result.getId().equals("generated-id") &&
                    result.getStatus() == LoanStatus.PENDING_REVIEW &&
                    result.getCustomerDocument().equals("1234567890")
                )
                .verifyComplete();
    }

    @Test
    void shouldFailWhenApplicationIsNull() {
        when(tx.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        StepVerifier.create(useCase.execute(null, TEST_USER_ID))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenAmountIsNull() {
        var input = LoanApplication.builder()
                .amount(null)
                .termMonths(12)
                .loanType("PERSONAL")
                .build();

        when(tx.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(useCase.execute(input, TEST_USER_ID))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenAmountIsZero() {
        var input = LoanApplication.builder()
                .amount(BigDecimal.ZERO)
                .termMonths(12)
                .loanType("PERSONAL")
                .build();

        when(tx.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(useCase.execute(input, TEST_USER_ID))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenLoanTypeDoesNotExist() {
        var input = LoanApplication.builder()
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("INVALID_TYPE")
                .build();

        when(tx.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(loanTypeRepository.existsActiveByCode("INVALID_TYPE")).thenReturn(Mono.just(false));

        StepVerifier.create(useCase.execute(input, TEST_USER_ID))
                .expectError(InvalidLoanTypeException.class)
                .verify();
    }
}