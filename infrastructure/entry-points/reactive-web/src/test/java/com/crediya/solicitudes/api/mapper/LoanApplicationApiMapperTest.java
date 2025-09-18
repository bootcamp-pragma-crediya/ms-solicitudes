package com.crediya.solicitudes.api.mapper;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LoanApplicationApiMapperTest {

    @Test
    void shouldMapRequestToDomain() {
        // Given
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                BigDecimal.valueOf(10000), 12, "PERSONAL");

        // When
        LoanApplication result = LoanApplicationApiMapper.toDomain(request);

        // Then
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(10000));
        assertThat(result.getTermMonths()).isEqualTo(12);
        assertThat(result.getLoanType()).isEqualTo("PERSONAL");
    }

    @Test
    void shouldMapDomainToResponse() {
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
                .baseSalary(new BigDecimal("5000000"))
                .interestRate(new BigDecimal("15.5"))
                .monthlyPayment(new BigDecimal("950"))

                .createdAt(OffsetDateTime.now())
                .build();

        // When
        var response = LoanApplicationApiMapper.toResponse(application);

        // Then
        assertThat(response.id()).isEqualTo("test-id");
        assertThat(response.amount()).isEqualTo(new BigDecimal("10000"));
        assertThat(response.termMonths()).isEqualTo(12);
        assertThat(response.loanType()).isEqualTo("PERSONAL");
        assertThat(response.status()).isEqualTo("PENDING_REVIEW");
        assertThat(response.customerDocument()).isEqualTo("123456789");
        assertThat(response.createdAt()).isNotNull();
    }
}