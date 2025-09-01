package com.crediya.solicitudes.api.mapper;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanApplicationResponse;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;

public final class LoanApplicationApiMapper {
    private LoanApplicationApiMapper() {}

    public static LoanApplication toDomain(CreateLoanRequestRequest dto) {
        return LoanApplication.builder()
                .customerDocument(dto.customerDocument())
                .amount(dto.amount())
                .termMonths(dto.termMonths())
                .loanType(dto.loanType())
                .build();
    }

    public static LoanApplicationResponse toResponse(LoanApplication app) {
        return new LoanApplicationResponse(
                app.getId(),
                app.getStatus().name(),
                app.getCustomerDocument(),
                app.getAmount(),
                app.getTermMonths(),
                app.getLoanType(),
                app.getCreatedAt()
        );
    }
}