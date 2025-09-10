package com.crediya.solicitudes.r2dbc.config;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.solicitudes.model.loantype.LoanType;
import com.crediya.solicitudes.model.loantype.gateways.LoanTypeRepository;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Configuration
@ConditionalOnProperty(name = "app.mock-mode", havingValue = "true", matchIfMissing = true)
public class MockRepositoryConfig {

    @Bean
    @Primary
    public LoanApplicationRepository mockLoanApplicationRepository() {
        return new LoanApplicationRepository() {
            @Override
            public Mono<LoanApplication> save(LoanApplication loanApplication) {
                return Mono.just(loanApplication.toBuilder()
                    .id("mock-id-" + System.currentTimeMillis())
                    .build());
            }

            @Override
            public Flux<LoanApplication> findByStatusIn(List<LoanStatus> statuses, int page, int size) {
                var mockApp = LoanApplication.builder()
                    .id("mock-1")
                    .customerDocument("12345678")
                    .customerName("Mock Customer")
                    .email("mock@example.com")
                    .amount(new BigDecimal("10000"))
                    .termMonths(12)
                    .loanType("PERSONAL")
                    .status(LoanStatus.PENDING_REVIEW)
                    .interestRate(new BigDecimal("15.5"))
                    .baseSalary(new BigDecimal("3000"))
                    .monthlyPayment(new BigDecimal("900"))
                    .createdAt(OffsetDateTime.now())
                    .build();
                
                return Flux.just(mockApp);
            }

            @Override
            public Mono<Long> countByStatusIn(List<LoanStatus> statuses) {
                return Mono.just(1L);
            }
        };
    }

    @Bean
    @Primary
    public LoanTypeRepository mockLoanTypeRepository() {
        return new LoanTypeRepository() {
            @Override
            public Mono<Boolean> existsActiveByCode(String code) {
                return Mono.just(List.of("PERSONAL", "MORTGAGE", "AUTO", "BUSINESS").contains(code));
            }
        };
    }
}