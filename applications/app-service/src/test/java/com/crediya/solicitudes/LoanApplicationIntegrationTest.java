package com.crediya.solicitudes;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanApplicationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.solicitudes.model.loantype.gateways.LoanTypeRepository;
import com.crediya.solicitudes.model.loantype.LoanType;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "spring.r2dbc.url=r2dbc:h2:mem:///testdb",
    "spring.sql.init.mode=never",
    "spring.main.allow-bean-definition-overriding=true",
    "logging.level.com.crediya=DEBUG"
})
class LoanApplicationIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    
    @MockitoBean
    private LoanApplicationRepository loanApplicationRepository;
    
    @MockitoBean
    private LoanTypeRepository loanTypeRepository;

    @Test
    void shouldCreateLoanApplicationSuccessfully() {
        // Mock loan type validation
        when(loanTypeRepository.existsActiveByCode("PERSONAL"))
            .thenReturn(Mono.just(true));
        
        // Mock loan application save
        when(loanApplicationRepository.save(any()))
            .thenAnswer(invocation -> {
                var loan = invocation.getArgument(0, com.crediya.solicitudes.model.loanapplication.LoanApplication.class);
                return Mono.just(loan.toBuilder()
                    .id("test-id")
                    .status(com.crediya.solicitudes.model.loanstatus.LoanStatus.PENDING_REVIEW)
                    .createdAt(java.time.OffsetDateTime.now())
                    .build());
            });
        
        var request = new CreateLoanRequestRequest(
            "12345678901",
            new BigDecimal("50000.00"),
            24,
            "PERSONAL"
        );

        webTestClient.post()
                .uri("/api/v1/solicitud")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody(LoanApplicationResponse.class)
                .value(response -> {
                    assert response.customerDocument().equals("12345678901");
                    assert response.amount().equals(new BigDecimal("50000.00"));
                    assert response.termMonths().equals(24);
                    assert response.loanType().equals("PERSONAL");
                    assert response.status().equals("PENDING_REVIEW");
                    assert response.id() != null;
                    assert response.createdAt() != null;
                });
    }

    @Test
    void shouldFailWithInvalidLoanType() {
        // Mock loan type not found
        when(loanTypeRepository.existsActiveByCode("INVALID_TYPE"))
            .thenReturn(Mono.just(false));
        
        var request = new CreateLoanRequestRequest(
            "12345678901",
            new BigDecimal("50000.00"),
            24,
            "INVALID_TYPE"
        );

        webTestClient.post()
                .uri("/api/v1/solicitud")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").exists();
    }

    @Test
    void shouldFailWithInvalidAmount() {
        var request = new CreateLoanRequestRequest(
            "12345678901",
            BigDecimal.ZERO,
            24,
            "PERSONAL"
        );

        webTestClient.post()
                .uri("/api/v1/solicitud")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void shouldFailWithEmptyDocument() {
        var request = new CreateLoanRequestRequest(
            "",
            new BigDecimal("50000.00"),
            24,
            "PERSONAL"
        );

        webTestClient.post()
                .uri("/api/v1/solicitud")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is4xxClientError();
    }
}