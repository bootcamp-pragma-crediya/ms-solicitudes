package com.crediya.solicitudes;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanApplicationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "adapters.r2dbc.host=localhost",
    "adapters.r2dbc.database=test_crediya",
    "spring.sql.init.mode=always"
})
class LoanApplicationIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldCreateLoanApplicationSuccessfully() {
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
                .expectStatus().isBadRequest();
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
                .expectStatus().isBadRequest();
    }
}