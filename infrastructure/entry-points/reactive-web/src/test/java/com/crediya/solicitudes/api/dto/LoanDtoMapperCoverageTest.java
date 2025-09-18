package com.crediya.solicitudes.api.dto;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {LoanDtoMapperImpl.class})
@TestPropertySource(properties = "app.mock-mode=true")
class LoanDtoMapperCoverageTest {

    @Autowired
    private LoanDtoMapper mapper;

    @Test
    void safeToDomain_ShouldReturnEmptyBuilderWhenRequestIsNull() {
        LoanApplication result = mapper.safeToDomain(null);
        
        assertNotNull(result);
        assertNull(result.getId());
    }

    @Test
    void safeToDomain_ShouldReturnMappedDomainWhenRequestIsValid() {
        CreateLoanRequestRequest request = new CreateLoanRequestRequest(
                java.math.BigDecimal.valueOf(10000), 12, "PERSONAL", "12345678"
        );
        
        LoanApplication result = mapper.safeToDomain(request);
        
        assertNotNull(result);
        assertEquals(java.math.BigDecimal.valueOf(10000), result.getAmount());
    }
}