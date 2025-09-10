package com.crediya.solicitudes.api.dto;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LoanDtoMapperTest {

    private final LoanDtoMapper mapper = Mappers.getMapper(LoanDtoMapper.class);
    
    @Test
    void shouldMapRequestToDomain() {
        // Given
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                "12345678", BigDecimal.valueOf(10000), 12, "PERSONAL");
        
        // When
        LoanApplication result = mapper.toDomain(request);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCustomerDocument()).isEqualTo("12345678");
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(10000));
        assertThat(result.getTermMonths()).isEqualTo(12);
        assertThat(result.getLoanType()).isEqualTo("PERSONAL");
        assertThat(result.getId()).isNull();
        assertThat(result.getStatus()).isNull();
        assertThat(result.getCreatedAt()).isNull();
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
        LoanApplicationResponse result = mapper.toResponse(app);
        
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
    void shouldMapDomainToListResponse() {
        // Given
        LoanApplication app = LoanApplication.builder()
                .id("1")
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .status(LoanStatus.PENDING_REVIEW)
                .email("test@test.com")
                .customerName("Test User")
                .build();
        
        // When
        LoanApplicationListResponse result = mapper.toListResponse(app);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("1");
        assertThat(result.email()).isEqualTo("test@test.com");
        assertThat(result.customerName()).isEqualTo("Test User");
    }
    
    @Test
    void shouldMapPagedResult() {
        // Given
        LoanApplication app = LoanApplication.builder()
                .id("1")
                .customerDocument("12345678")
                .status(LoanStatus.PENDING_REVIEW)
                .build();
                
        ListLoanApplicationsUseCase.PagedResult<LoanApplication> pagedResult = 
                ListLoanApplicationsUseCase.PagedResult.<LoanApplication>builder()
                    .content(List.of(app))
                    .page(0)
                    .size(10)
                    .totalElements(1L)
                    .totalPages(1)
                    .build();
        
        // When
        PagedResponse<LoanApplicationListResponse> result = mapper.toPagedResponse(pagedResult);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.content()).hasSize(1);
        assertThat(result.page()).isEqualTo(0);
        assertThat(result.size()).isEqualTo(10);
        assertThat(result.totalElements()).isEqualTo(1L);
        assertThat(result.totalPages()).isEqualTo(1);
    }
    
    @Test
    void shouldHandleNullRequestSafely() {
        // When
        LoanApplication result = mapper.safeToDomain(null);
        
        // Then
        assertThat(result).isNotNull();
    }
    
    @Test
    void shouldMapListOfApplications() {
        // Given
        List<LoanApplication> apps = List.of(
                LoanApplication.builder().id("1").customerDocument("123").build(),
                LoanApplication.builder().id("2").customerDocument("456").build()
        );
        
        // When
        List<LoanApplicationListResponse> result = mapper.toListResponse(apps);
        
        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo("1");
        assertThat(result.get(1).id()).isEqualTo("2");
    }
}