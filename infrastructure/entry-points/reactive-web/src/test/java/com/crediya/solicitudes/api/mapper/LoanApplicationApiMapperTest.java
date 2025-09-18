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
    void shouldThrowExceptionWhenInstantiated() {
        assertThrows(UnsupportedOperationException.class, () -> {
            try {
                var constructor = LoanApplicationApiMapper.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        });
    }

    @Test
    void shouldMapCreateRequestToDomain() {
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                BigDecimal.valueOf(10000),
                12,
                "PERSONAL",
                "12345678"
        );

        LoanApplication result = LoanApplicationApiMapper.toDomain(request);

        assertEquals(BigDecimal.valueOf(10000), result.getAmount());
        assertEquals(12, result.getTermMonths());
        assertEquals("PERSONAL", result.getLoanType());
        assertEquals("12345678", result.getCustomerDocument());
    }

    @Test
    void shouldMapDomainToResponse() {
        OffsetDateTime now = OffsetDateTime.now();
        LoanApplication app = LoanApplication.builder()
                .id("1")
                .userId("user1")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .customerDocument("12345678")
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(now)
                .build();

        LoanApplicationResponse result = LoanApplicationApiMapper.toResponse(app);

        assertEquals("1", result.id());
        assertEquals("user1", result.userId());
        assertEquals("PENDING_REVIEW", result.status());
        assertEquals("12345678", result.customerDocument());
        assertEquals(BigDecimal.valueOf(10000), result.amount());
        assertEquals(12, result.termMonths());
        assertEquals("PERSONAL", result.loanType());
        assertEquals(now, result.createdAt());
    }

    @Test
    void shouldMapDomainToResponseWithNullCreatedAt() {
        LoanApplication app = LoanApplication.builder()
                .id("1")
                .userId("user1")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .customerDocument("12345678")
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(null)
                .build();

        LoanApplicationResponse result = LoanApplicationApiMapper.toResponse(app);

        assertNotNull(result.createdAt());
        assertEquals("1", result.id());
    }
}