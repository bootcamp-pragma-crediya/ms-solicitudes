package com.crediya.solicitudes.api.dto;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanDtoMapper {
    LoanApplication toDomain(CreateLoanRequestRequest request);
    LoanApplicationResponse toResponse(LoanApplication app);
}