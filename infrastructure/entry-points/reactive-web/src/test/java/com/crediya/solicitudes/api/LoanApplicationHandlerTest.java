package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanApplicationListResponse;
import com.crediya.solicitudes.api.dto.LoanApplicationResponse;
import com.crediya.solicitudes.api.dto.LoanDtoMapper;
import com.crediya.solicitudes.api.dto.PagedResponse;
import com.crediya.solicitudes.model.exception.InvalidLoanApplicationException;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanApplicationHandlerTest {

    @Mock
    private LoanApplicationUseCase useCase;
    
    @Mock
    private ListLoanApplicationsUseCase listUseCase;
    
    @Mock
    private LoanDtoMapper mapper;
    
    private LoanApplicationHandler handler;
    
    @BeforeEach
    void setUp() {
        handler = new LoanApplicationHandler(useCase, listUseCase, mapper);
    }
    
    @Test
    void shouldCreateLoanApplication() {
        // Given
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                "12345678", BigDecimal.valueOf(10000), 12, "PERSONAL");
        
        LoanApplication domain = LoanApplication.builder()
                .customerDocument("12345678")
                .amount(BigDecimal.valueOf(10000))
                .termMonths(12)
                .loanType("PERSONAL")
                .build();
                
        LoanApplication saved = domain.toBuilder()
                .id("1")
                .status(LoanStatus.PENDING_REVIEW)
                .createdAt(OffsetDateTime.now())
                .build();
                
        LoanApplicationResponse response = new LoanApplicationResponse(
                "1", "PENDING_REVIEW", "12345678", BigDecimal.valueOf(10000), 
                12, "PERSONAL", OffsetDateTime.now());
        
        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(request));
        
        when(mapper.toDomain(request)).thenReturn(domain);
        when(useCase.execute(domain)).thenReturn(Mono.just(saved));
        when(mapper.toResponse(saved)).thenReturn(response);
        
        // When & Then
        StepVerifier.create(handler.create(serverRequest))
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }
    
    @Test
    void shouldHandleInvalidRequest() {
        // Given
        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(new CreateLoanRequestRequest("", BigDecimal.ZERO, 0, "")));
        
        when(mapper.toDomain(any())).thenReturn(null);
        
        // When & Then
        StepVerifier.create(handler.create(serverRequest))
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }
    
    @Test
    void shouldListLoanApplications() {
        // Given
        ListLoanApplicationsUseCase.PagedResult<LoanApplication> pagedResult = 
                ListLoanApplicationsUseCase.PagedResult.<LoanApplication>builder()
                    .content(List.of())
                    .page(0)
                    .size(10)
                    .totalElements(0L)
                    .totalPages(0)
                    .build();
        
        PagedResponse<LoanApplicationListResponse> response = new PagedResponse<>(List.of(), 0, 10, 0L, 0);
        
        ServerRequest serverRequest = MockServerRequest.builder().build();
        
        when(listUseCase.execute(0, 10)).thenReturn(Mono.just(pagedResult));
        when(mapper.toPagedResponse(pagedResult)).thenReturn(response);
        
        // When & Then
        StepVerifier.create(handler.list(serverRequest))
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }
    
    @Test
    void shouldHandleUseCaseError() {
        // Given
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                "12345678", BigDecimal.valueOf(10000), 12, "PERSONAL");
        
        LoanApplication domain = LoanApplication.builder().build();
        
        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(request));
        
        when(mapper.toDomain(request)).thenReturn(domain);
        when(useCase.execute(domain)).thenReturn(Mono.error(new InvalidLoanApplicationException("Error")));
        
        // When & Then
        StepVerifier.create(handler.create(serverRequest))
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }
}