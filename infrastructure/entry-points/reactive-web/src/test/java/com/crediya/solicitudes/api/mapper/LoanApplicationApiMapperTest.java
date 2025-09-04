package com.crediya.solicitudes.api.mapper;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanApplicationResponse;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationApiMapperTest {

    @Test
    void shouldMapCreateLoanRequestToDomain() {
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                "12345678",
                new BigDecimal("10000.00"),
                12,
                "PERSONAL"
        );

        LoanApplication result = LoanApplicationApiMapper.toDomain(request);

        assertEquals("12345678", result.getCustomerDocument());
        assertEquals(new BigDecimal("10000.00"), result.getAmount());
        assertEquals(12, result.getTermMonths());
        assertEquals("PERSONAL", result.getLoanType());
    }

    @Test
    void shouldMapLoanApplicationToResponse() {
        OffsetDateTime now = OffsetDateTime.now();
        LoanApplication application = LoanApplication.builder()
                .id("app-123")
                .status(LoanStatus.PENDING_REVIEW)
                .customerDocument("12345678")
                .amount(new BigDecimal("10000.00"))
                .termMonths(12)
                .loanType("PERSONAL")
                .createdAt(now)
                .build();

        LoanApplicationResponse result = LoanApplicationApiMapper.toResponse(application);

        assertEquals("app-123", result.id());
        assertEquals("PENDING_REVIEW", result.status());
        assertEquals("12345678", result.customerDocument());
        assertEquals(new BigDecimal("10000.00"), result.amount());
        assertEquals(12, result.termMonths());
        assertEquals("PERSONAL", result.loanType());
        assertEquals(now, result.createdAt());
    }
}