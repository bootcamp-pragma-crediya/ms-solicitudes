package com.crediya.solicitudes.config;

import com.crediya.solicitudes.model.common.ReactiveTransaction;
import com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.solicitudes.model.loantype.gateways.LoanTypeRepository;


import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

        @Bean
        public ListLoanApplicationsUseCase listLoanApplicationsUseCase(
                LoanApplicationRepository loanApplicationRepository
        ) {
                return new ListLoanApplicationsUseCase(loanApplicationRepository);
        }
        

}
