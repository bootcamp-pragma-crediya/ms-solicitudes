package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.r2dbc.LoanApplicationData;
import com.crediya.solicitudes.r2dbc.LoanApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoanApplicationRepositoryAdapterTest {

    @Test
    void shouldCreateAdapter() {
        // Simple test to verify adapter can be instantiated
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> {
            var repository = org.mockito.Mockito.mock(LoanApplicationRepository.class);
            var mapper = org.mockito.Mockito.mock(org.reactivecommons.utils.ObjectMapper.class);
            new LoanApplicationRepositoryAdapter(repository, mapper);
        });
    }
}