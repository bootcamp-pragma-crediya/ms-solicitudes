package com.crediya.solicitudes.api.dto;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanDtoMapperTest {

    private final LoanDtoMapper mapper = Mappers.getMapper(LoanDtoMapper.class);

    @Test
    void shouldMapRequestToDomain() {
        var request = new CreateLoanRequestRequest(
            "12345678",
            new BigDecimal("10000"),
            12,
            "PERSONAL"
        );

        var domain = mapper.toDomain(request);

        assertEquals("12345678", domain.getCustomerDocument());
        assertEquals(new BigDecimal("10000"), domain.getAmount());
        assertEquals(12, domain.getTermMonths());
        assertEquals("PERSONAL", domain.getLoanType());
        assertNull(domain.getId());
        assertNull(domain.getStatus());
        assertNull(domain.getCreatedAt());
    }

    @Test
    void shouldMapDomainToResponse() {
        var domain = LoanApplication.builder()
                .id("test-id")
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build();

        var response = mapper.toResponse(domain);

        assertEquals("test-id", response.id());
        assertEquals("12345678", response.customerDocument());
        assertEquals(new BigDecimal("10000"), response.amount());
        assertEquals(12, response.termMonths());
        assertEquals("PERSONAL", response.loanType());
        assertEquals("PENDING_REVIEW", response.status());
        assertNotNull(response.createdAt());
    }
}