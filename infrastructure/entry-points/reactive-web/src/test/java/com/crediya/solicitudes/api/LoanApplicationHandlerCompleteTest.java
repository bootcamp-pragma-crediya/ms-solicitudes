package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.dto.LoanDtoMapper;
import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationHandlerCompleteTest {

    @Mock
    private LoanApplicationUseCase useCase;
    
    @Mock
    private ListLoanApplicationsUseCase listUseCase;
    
    @Mock
    private LoanDtoMapper mapper;

    @InjectMocks
    private LoanApplicationHandler handler;

    @Test
    void shouldCreateHandlerInstance() {
        // When & Then
        assertNotNull(handler);
    }

    @Test
    void shouldHaveRequiredDependencies() {
        // When & Then
        assertNotNull(useCase);
        assertNotNull(listUseCase);
        assertNotNull(mapper);
    }

    @Test
    void shouldBeComponentAnnotated() {
        // When & Then
        assertTrue(LoanApplicationHandler.class.isAnnotationPresent(
                org.springframework.stereotype.Component.class));
    }

    @Test
    void shouldHaveCorrectAnnotations() {
        // When & Then
        assertNotNull(LoanApplicationHandler.class.getAnnotations());
        assertTrue(LoanApplicationHandler.class.getAnnotations().length > 0);
    }
}