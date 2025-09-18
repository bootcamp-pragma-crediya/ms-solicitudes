package com.crediya.solicitudes.api.dto;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LoanDtoMapperImplTest {

    private final LoanDtoMapperImpl mapper = new LoanDtoMapperImpl();

    @Test
    void shouldMapToResponse() {
        // Given
        var application = LoanApplication.builder()
                .id("test-id")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .customerDocument("123456789")
                .customerName("Test User")
                .email("test@email.com")
                .createdAt(OffsetDateTime.now())
                .build();

        // When
        LoanApplicationResponse response = mapper.toResponse(application);

        // Then
        assertThat(response.id()).isEqualTo("test-id");
        assertThat(response.amount()).isEqualTo(new BigDecimal("10000"));
        assertThat(response.termMonths()).isEqualTo(12);
        assertThat(response.loanType()).isEqualTo("PERSONAL");
        assertThat(response.status()).isEqualTo("PENDING_REVIEW");
    }

    @Test
    void shouldMapToListResponse() {
        // Given
        var application = LoanApplication.builder()
                .id("test-id")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .customerDocument("123456789")
                .customerName("Test User")
                .email("test@email.com")
                .interestRate(new BigDecimal("15.5"))
                .monthlyPayment(new BigDecimal("950"))

                .build();

        // When
        LoanApplicationListResponse response = mapper.toListResponse(application);

        // Then
        assertThat(response.id()).isEqualTo("test-id");
        assertThat(response.amount()).isEqualTo(new BigDecimal("10000"));
        assertThat(response.termMonths()).isEqualTo(12);
        assertThat(response.loanType()).isEqualTo("PERSONAL");
        assertThat(response.status()).isEqualTo("PENDING_REVIEW");
    }

    @Test
    void shouldMapListToListResponse() {
        // Given
        var app1 = LoanApplication.builder()
                .id("test-id-1")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .customerDocument("123456789")
                .build();

        var app2 = LoanApplication.builder()
                .id("test-id-2")
                .amount(new BigDecimal("20000"))
                .termMonths(24)
                .loanType("VEHICLE")
                .status(LoanStatus.APPROVED)
                .customerDocument("987654321")
                .build();

        List<LoanApplication> applications = Arrays.asList(app1, app2);

        // When
        List<LoanApplicationListResponse> responses = mapper.toListResponse(applications);

        // Then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).id()).isEqualTo("test-id-1");
        assertThat(responses.get(1).id()).isEqualTo("test-id-2");
    }

    @Test
    void shouldMapToPagedResponse() {
        // Given
        var pagedResult = new com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase.PagedResult(
                Arrays.asList(), 0, 10, 0L, 0);

        // When
        PagedResponse<LoanApplicationListResponse> response = mapper.toPagedResponse(pagedResult);

        // Then
        assertThat(response.content()).isEmpty();
        assertThat(response.page()).isEqualTo(0);
        assertThat(response.size()).isEqualTo(10);
        assertThat(response.totalElements()).isEqualTo(0L);
        assertThat(response.totalPages()).isEqualTo(0);
    }
}