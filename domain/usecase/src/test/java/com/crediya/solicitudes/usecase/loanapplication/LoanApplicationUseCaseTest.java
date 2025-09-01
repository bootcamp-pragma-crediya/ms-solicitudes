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
import static org.mockito.ArgumentMatchers.anyString;
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

    @BeforeEach
    void setUp() {
        useCase = new LoanApplicationUseCase(loanApplicationRepository, loanTypeRepository, tx);
    }

    @Test
    void shouldCreateLoanApplicationSuccessfully() {
        // Given
        var input = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .build();

        var saved = input.toBuilder()
                .id("generated-id")
                .status(LoanStatus.PENDING_REVIEW)
                .build();

        when(loanTypeRepository.existsActiveByCode("PERSONAL")).thenReturn(Mono.just(true));
        when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.just(saved));
        when(tx.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When & Then
        StepVerifier.create(useCase.execute(input))
                .expectNext(saved)
                .verifyComplete();
    }

    @Test
    void shouldFailWhenApplicationIsNull() {
        StepVerifier.create(useCase.execute(null))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenCustomerDocumentIsNull() {
        var input = LoanApplication.builder()
                .customerDocument(null)
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .build();

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenCustomerDocumentIsBlank() {
        var input = LoanApplication.builder()
                .customerDocument("   ")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .build();

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenAmountIsNull() {
        var input = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(null)
                .termMonths(12)
                .loanType("PERSONAL")
                .build();

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenAmountIsZero() {
        var input = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(BigDecimal.ZERO)
                .termMonths(12)
                .loanType("PERSONAL")
                .build();

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenAmountIsNegative() {
        var input = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("-1000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .build();

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenTermMonthsIsNull() {
        var input = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(null)
                .loanType("PERSONAL")
                .build();

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenTermMonthsIsZero() {
        var input = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(0)
                .loanType("PERSONAL")
                .build();

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenTermMonthsIsNegative() {
        var input = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(-5)
                .loanType("PERSONAL")
                .build();

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenLoanTypeIsNull() {
        var input = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType(null)
                .build();

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenLoanTypeIsBlank() {
        var input = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("   ")
                .build();

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanApplicationException.class)
                .verify();
    }

    @Test
    void shouldFailWhenLoanTypeDoesNotExist() {
        var input = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("INVALID_TYPE")
                .build();

        when(tx.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(loanTypeRepository.existsActiveByCode("INVALID_TYPE")).thenReturn(Mono.just(false));

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanTypeException.class)
                .verify();
    }

    @Test
    void shouldFailWhenLoanTypeIsInactive() {
        var input = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("INACTIVE_TYPE")
                .build();

        when(tx.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(loanTypeRepository.existsActiveByCode("INACTIVE_TYPE")).thenReturn(Mono.just(false));

        StepVerifier.create(useCase.execute(input))
                .expectError(InvalidLoanTypeException.class)
                .verify();
    }
}