package com.crediya.solicitudes.api.mapper;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanApplicationResponse;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoanApplicationApiMapperTest {

    @Test
    void shouldMapRequestToDomain() {
        // Given
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                "12345678", BigDecimal.valueOf(10000), 12, "PERSONAL");
        
        // When
        LoanApplication result = LoanApplicationApiMapper.toDomain(request);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCustomerDocument()).isEqualTo("12345678");
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(10000));
        assertThat(result.getTermMonths()).isEqualTo(12);
        assertThat(result.getLoanType()).isEqualTo("PERSONAL");
    }
    
    @Test
    void shouldMapDomainToResponse() {
        // Given
        LoanApplication app = LoanApplication.builder()
                .id("1")
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build();
        
        // When
        LoanApplicationResponse result = LoanApplicationApiMapper.toResponse(app);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("1");
        assertThat(result.customerDocument()).isEqualTo("12345678");
        assertThat(result.amount()).isEqualTo(BigDecimal.valueOf(10000));
        assertThat(result.termMonths()).isEqualTo(12);
        assertThat(result.loanType()).isEqualTo("PERSONAL");
        assertThat(result.status()).isEqualTo("PENDING_REVIEW");
    }
    
    @Test
    void shouldNotBeInstantiable() throws Exception {
        Constructor<LoanApplicationApiMapper> constructor = LoanApplicationApiMapper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        
        assertThatThrownBy(constructor::newInstance)
                .hasCauseInstanceOf(UnsupportedOperationException.class);
    }
}