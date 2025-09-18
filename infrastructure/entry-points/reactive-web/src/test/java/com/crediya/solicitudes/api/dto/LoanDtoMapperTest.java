package com.crediya.solicitudes.api.dto;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanDtoMapperTest {

    private final LoanDtoMapper mapper = Mappers.getMapper(LoanDtoMapper.class);

    @Test
    void toDomain_ShouldMapRequestToLoanApplication() {
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                new BigDecimal("10000"), 12, "PERSONAL", "12345678"
        );

        LoanApplication result = mapper.toDomain(request);

        assertNotNull(result);
        assertEquals("12345678", result.getCustomerDocument());
        assertEquals("PERSONAL", result.getLoanType());
        assertEquals(new BigDecimal("10000"), result.getAmount());
        assertEquals(12, result.getTermMonths());
    }

    @Test
    void toResponse_ShouldMapLoanApplicationToResponse() {
        LoanApplication app = LoanApplication.builder()
                .id("1")
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build();

        LoanApplicationResponse result = mapper.toResponse(app);

        assertNotNull(result);
        assertEquals("1", result.id());
        assertEquals("12345678", result.customerDocument());
        assertEquals(new BigDecimal("10000"), result.amount());
    }

    @Test
    void toListResponse_ShouldMapLoanApplicationToListResponse() {
        LoanApplication app = LoanApplication.builder()
                .id("1")
                .customerDocument("12345678")
                .amount(new BigDecimal("10000"))
                .status(LoanStatus.PENDING_REVIEW)
                .build();

        LoanApplicationListResponse result = mapper.toListResponse(app);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("12345678", result.getCustomerDocument());
    }

    @Test
    void toListResponse_WithList_ShouldMapAllApplications() {
        List<LoanApplication> apps = List.of(
                LoanApplication.builder().id("1").customerDocument("123").build(),
                LoanApplication.builder().id("2").customerDocument("456").build()
        );

        List<LoanApplicationListResponse> result = mapper.toListResponse(apps);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
    }

    @Test
    void toPagedResponse_ShouldMapPagedResult() {
        List<LoanApplication> apps = List.of(
                LoanApplication.builder().id("1").build()
        );
        ListLoanApplicationsUseCase.PagedResult<LoanApplication> pagedResult = 
                new ListLoanApplicationsUseCase.PagedResult<>(apps, 0, 10, 1, 1);

        PagedResponseClass<LoanApplicationListResponse> result = mapper.toPagedResponse(pagedResult);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(0, result.getPage());
        assertEquals(10, result.getSize());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void safeToDomain_WithNullRequest_ShouldReturnEmptyLoanApplication() {
        LoanApplication result = mapper.safeToDomain(null);

        assertNotNull(result);
    }

    @Test
    void safeToDomain_WithValidRequest_ShouldReturnMappedLoanApplication() {
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                new BigDecimal("10000"), 12, "PERSONAL", "12345678"
        );

        LoanApplication result = mapper.safeToDomain(request);

        assertNotNull(result);
        assertEquals("12345678", result.getCustomerDocument());
    }
}