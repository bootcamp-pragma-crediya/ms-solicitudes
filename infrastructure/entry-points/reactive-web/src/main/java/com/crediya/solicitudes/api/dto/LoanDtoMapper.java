package com.crediya.solicitudes.api.dto;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "customerName", ignore = true)
    @Mapping(target = "interestRate", ignore = true)
    @Mapping(target = "baseSalary", ignore = true)
    @Mapping(target = "monthlyPayment", ignore = true)
    LoanApplication toDomain(CreateLoanRequestRequest request);
    
    LoanApplicationResponse toResponse(LoanApplication app);
    
    LoanApplicationListResponse toListResponse(LoanApplication app);
    
    List<LoanApplicationListResponse> toListResponse(List<LoanApplication> apps);
    
    default PagedResponse<LoanApplicationListResponse> toPagedResponse(ListLoanApplicationsUseCase.PagedResult<LoanApplication> pagedResult) {
        return new PagedResponse<>(
            toListResponse(pagedResult.getContent()),
            pagedResult.getPage(),
            pagedResult.getSize(),
            pagedResult.getTotalElements(),
            pagedResult.getTotalPages()
        );
    }
    
    default LoanApplication safeToDomain(CreateLoanRequestRequest request) {
        if (request == null) {
            return LoanApplication.builder().build();
        }
        LoanApplication result = toDomain(request);
        return result != null ? result : LoanApplication.builder().build();
    }
}