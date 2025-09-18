package com.crediya.solicitudes.api.dto;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class LoanDtoMapperTest {

    private final LoanDtoMapper mapper = new LoanDtoMapperImpl();

    @Test
    void shouldMapRequestToDomain() {
        // Given
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                BigDecimal.valueOf(10000), 12, "PERSONAL");

        // When
        LoanApplication result = mapper.toDomain(request);

        // Then
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(10000));
        assertThat(result.getTermMonths()).isEqualTo(12);
        assertThat(result.getLoanType()).isEqualTo("PERSONAL");
    }
}