package com.crediya.solicitudes.api.mapper;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.CreateLoanApplicationResponse;
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
        return new LoanApplicationResponse(
                app.getId(),
                app.getEmail(),
                app.getUserId(),
                app.getCustomerDocument(),
                app.getAmount(),
                app.getTermMonths(),
                app.getCustomerName(),
                app.getLoanType(),
                app.getStatus().name(),
                app.getBaseSalary()
        );
    }

    public static CreateLoanApplicationResponse toCreateResponse(LoanApplication app) {
        return new CreateLoanApplicationResponse(
                app.getId(),
                app.getEmail(),
                app.getUserId(),
                app.getCustomerDocument(),
                app.getAmount(),
                app.getTermMonths(),
                app.getLoanType(),
                app.getStatus().name()
        );
    }
}