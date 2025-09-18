package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MockLoanApplicationRepositoryAdapterTest {

    @InjectMocks
    private MockLoanApplicationRepositoryAdapter adapter;

    @Test
    void save_ShouldReturnApplicationWithId() {
        LoanApplication application = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .build();

        StepVerifier.create(adapter.save(application))
                .assertNext(saved -> {
                    assertNotNull(saved.getId());
                    assertEquals("12345678", saved.getCustomerDocument());
                    assertEquals(new BigDecimal("10000"), saved.getAmount());
                })
                .verifyComplete();
    }

    @Test
    void findByStatusIn_ShouldReturnMockData() {
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW, LoanStatus.REJECTED);

        StepVerifier.create(adapter.findByStatusIn(statuses, 0, 10))
                .assertNext(app -> {
                    assertEquals("1", app.getId());
                    assertEquals("12345678", app.getCustomerDocument());
                    assertEquals("juan@example.com", app.getEmail());
                    assertEquals("Juan Pérez", app.getCustomerName());
                    assertEquals(new BigDecimal("50000"), app.getAmount());
                    assertEquals(24, app.getTermMonths());
                    assertEquals("PERSONAL", app.getLoanType());
                    assertEquals(new BigDecimal("12.5"), app.getInterestRate());
                    assertEquals(new BigDecimal("3000000"), app.getBaseSalary());
                    assertEquals(new BigDecimal("2500"), app.getMonthlyPayment());
                    assertEquals(LoanStatus.PENDING_REVIEW, app.getStatus());
                    assertNotNull(app.getCreatedAt());
                })
                .assertNext(app -> {
                    assertEquals("2", app.getId());
                    assertEquals("87654321", app.getCustomerDocument());
                    assertEquals("maria@example.com", app.getEmail());
                    assertEquals("María García", app.getCustomerName());
                    assertEquals(new BigDecimal("100000"), app.getAmount());
                    assertEquals(36, app.getTermMonths());
                    assertEquals("HIPOTECARIO", app.getLoanType());
                    assertEquals(new BigDecimal("8.5"), app.getInterestRate());
                    assertEquals(new BigDecimal("5000000"), app.getBaseSalary());
                    assertEquals(new BigDecimal("3200"), app.getMonthlyPayment());
                    assertEquals(LoanStatus.REJECTED, app.getStatus());
                    assertNotNull(app.getCreatedAt());
                })
                .verifyComplete();
    }

    @Test
    void findByStatusIn_WithPagination_ShouldSkipAndTake() {
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW);

        StepVerifier.create(adapter.findByStatusIn(statuses, 1, 1))
                .assertNext(app -> assertEquals("2", app.getId()))
                .verifyComplete();
    }

    @Test
    void countByStatusIn_ShouldReturnTwo() {
        List<LoanStatus> statuses = List.of(LoanStatus.PENDING_REVIEW, LoanStatus.REJECTED);

        StepVerifier.create(adapter.countByStatusIn(statuses))
                .expectNext(2L)
                .verifyComplete();
    }
}