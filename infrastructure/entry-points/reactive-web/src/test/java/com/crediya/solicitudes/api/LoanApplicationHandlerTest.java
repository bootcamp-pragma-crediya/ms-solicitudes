package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanApplicationResponse;
import com.crediya.solicitudes.api.dto.LoanDtoMapper;
import com.crediya.solicitudes.model.exception.InvalidLoanApplicationException;
import com.crediya.solicitudes.model.exception.InvalidLoanTypeException;
import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.model.loantype.LoanTypeCode;
import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanApplicationHandlerTest {

    @Mock
    private LoanApplicationUseCase useCase;
    
    @Mock
    private ListLoanApplicationsUseCase listUseCase;
    
    @Mock
    private LoanDtoMapper mapper;
    
    @Mock
    private ServerRequest request;
    
    @Mock
    private ServerRequest.Headers headers;

    private LoanApplicationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new LoanApplicationHandler(useCase, listUseCase, mapper);
    }

    @Test
    void shouldReturnBadRequestWhenUserIdMissing() {
        // Given
        when(request.headers()).thenReturn(headers);
        when(headers.firstHeader("X-User-Id")).thenReturn(null);

        // When & Then
        StepVerifier.create(handler.create(request))
            .expectNextMatches(serverResponse -> 
                serverResponse.statusCode().value() == 400)
            .verifyComplete();
    }

    @Test
    void shouldReturnBadRequestWhenUserIdBlank() {
        // Given
        when(request.headers()).thenReturn(headers);
        when(headers.firstHeader("X-User-Id")).thenReturn("  ");

        // When & Then
        StepVerifier.create(handler.create(request))
            .expectNextMatches(serverResponse -> 
                serverResponse.statusCode().value() == 400)
            .verifyComplete();
    }

    @Test
    void shouldHandleInvalidLoanApplicationException() {
        // Given
        String userId = "user123";
        CreateLoanRequestRequest requestDto = new CreateLoanRequestRequest(
            new BigDecimal("10000"), 
            12,
            "PERSONAL"
        );

        when(request.headers()).thenReturn(headers);
        when(headers.firstHeader("X-User-Id")).thenReturn(userId);
        when(request.bodyToMono(CreateLoanRequestRequest.class)).thenReturn(Mono.just(requestDto));
        when(mapper.toDomain(requestDto)).thenReturn(null);

        // When & Then
        StepVerifier.create(handler.create(request))
            .expectNextMatches(serverResponse -> 
                serverResponse.statusCode().value() == 400)
            .verifyComplete();
    }

    @Test
    void shouldHandleUseCaseException() {
        // Given
        String userId = "user123";
        CreateLoanRequestRequest requestDto = new CreateLoanRequestRequest(
            new BigDecimal("10000"), 
            12,
            "PERSONAL"
        );
        
        LoanApplication domain = LoanApplication.builder()
            .customerDocument("12345678")
            .loanType("PERSONAL")
            .amount(new BigDecimal("10000"))
            .termMonths(12)
            .build();

        when(request.headers()).thenReturn(headers);
        when(headers.firstHeader("X-User-Id")).thenReturn(userId);
        when(request.bodyToMono(CreateLoanRequestRequest.class)).thenReturn(Mono.just(requestDto));
        when(mapper.toDomain(requestDto)).thenReturn(domain);
        when(useCase.execute(domain, userId)).thenReturn(Mono.error(new InvalidLoanTypeException("Invalid type")));

        // When & Then
        StepVerifier.create(handler.create(request))
            .expectNextMatches(serverResponse -> 
                serverResponse.statusCode().value() == 400)
            .verifyComplete();
    }

    @Test
    void shouldHandleUnexpectedException() {
        // Given
        String userId = "user123";
        CreateLoanRequestRequest requestDto = new CreateLoanRequestRequest(
            new BigDecimal("10000"), 
            12,
            "PERSONAL"
        );
        
        LoanApplication domain = LoanApplication.builder()
            .customerDocument("12345678")
            .loanType("PERSONAL")
            .amount(new BigDecimal("10000"))
            .termMonths(12)
            .build();

        when(request.headers()).thenReturn(headers);
        when(headers.firstHeader("X-User-Id")).thenReturn(userId);
        when(request.bodyToMono(CreateLoanRequestRequest.class)).thenReturn(Mono.just(requestDto));
        when(mapper.toDomain(requestDto)).thenReturn(domain);
        when(useCase.execute(domain, userId)).thenReturn(Mono.error(new RuntimeException("Unexpected error")));

        // When & Then
        StepVerifier.create(handler.create(request))
            .expectNextMatches(serverResponse -> 
                serverResponse.statusCode().value() == 500)
            .verifyComplete();
    }

    @Test
    void shouldListWithDefaultPagination() {
        // Given
        when(request.queryParam("page")).thenReturn(Optional.empty());
        when(request.queryParam("size")).thenReturn(Optional.empty());
        when(listUseCase.execute(0, 10)).thenReturn(Mono.error(new RuntimeException("Test error")));

        // When & Then
        StepVerifier.create(handler.list(request))
            .expectNextMatches(serverResponse -> 
                serverResponse.statusCode().value() == 500)
            .verifyComplete();
    }

    @Test
    void shouldHandleListError() {
        // Given
        when(request.queryParam("page")).thenReturn(Optional.of("0"));
        when(request.queryParam("size")).thenReturn(Optional.of("10"));
        when(listUseCase.execute(0, 10)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // When & Then
        StepVerifier.create(handler.list(request))
            .expectNextMatches(serverResponse -> 
                serverResponse.statusCode().value() == 500)
            .verifyComplete();
    }
}