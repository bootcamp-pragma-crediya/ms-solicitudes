package com.crediya.solicitudes.config;

import com.crediya.solicitudes.model.common.ReactiveTransaction;
import com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.solicitudes.model.loantype.gateways.LoanTypeRepository;
import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
public class UseCasesConfig {
        @Bean
        public LoanApplicationUseCase loanApplicationUseCase(
                LoanApplicationRepository loanApplicationRepository,
                LoanTypeRepository loanTypeRepository,
                ReactiveTransaction reactiveTx
        ) {
                return new LoanApplicationUseCase(loanApplicationRepository, loanTypeRepository, reactiveTx);
        }
}
