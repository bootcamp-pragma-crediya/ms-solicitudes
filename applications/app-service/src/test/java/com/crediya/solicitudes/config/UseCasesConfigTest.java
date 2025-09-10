package com.crediya.solicitudes.config;

import com.crediya.solicitudes.model.common.ReactiveTransaction;
import com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.solicitudes.model.loantype.gateways.LoanTypeRepository;
import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UseCasesConfigTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;
    
    @Mock
    private LoanTypeRepository loanTypeRepository;
    
    @Mock
    private ReactiveTransaction reactiveTransaction;
    
    private UseCasesConfig config;
    
    @BeforeEach
    void setUp() {
        config = new UseCasesConfig();
    }
    
    @Test
    void shouldCreateLoanApplicationUseCase() {
        // When
        LoanApplicationUseCase useCase = config.loanApplicationUseCase(
                loanApplicationRepository, loanTypeRepository, reactiveTransaction);
        
        // Then
        assertThat(useCase).isNotNull();
    }
    
    @Test
    void shouldCreateListLoanApplicationsUseCase() {
        // When
        ListLoanApplicationsUseCase useCase = config.listLoanApplicationsUseCase(loanApplicationRepository);
        
        // Then
        assertThat(useCase).isNotNull();
    }
}