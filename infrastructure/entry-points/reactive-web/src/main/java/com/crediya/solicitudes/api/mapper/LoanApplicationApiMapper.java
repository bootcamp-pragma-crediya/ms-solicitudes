package com.crediya.solicitudes.api.mapper;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanApplicationResponse;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;

public final class LoanApplicationApiMapper {
    private LoanApplicationApiMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static LoanApplication toDomain(CreateLoanRequestRequest dto) {
        return LoanApplication.builder()
                .amount(dto.amount())
                .termMonths(dto.termMonths())
                .loanType(dto.loanType())
                .customerDocument(dto.customerDocument())
                .build();
    }

    public static LoanApplicationResponse toResponse(LoanApplication app) {
        System.out.println("[DEBUG] Mapping response - ID: " + app.getId());
        System.out.println("[DEBUG] CreatedAt from domain: " + app.getCreatedAt());
        
        LoanApplicationResponse response = new LoanApplicationResponse(
                app.getId(),
                app.getUserId(),
                app.getStatus().name(),
                app.getCustomerDocument(),
                app.getAmount(),
                app.getTermMonths(),
                app.getLoanType(),
                app.getCreatedAt() != null ? app.getCreatedAt() : java.time.OffsetDateTime.now()
        );
        
        System.out.println("[DEBUG] Response createdAt: " + response.createdAt());
        return response;
    }
}