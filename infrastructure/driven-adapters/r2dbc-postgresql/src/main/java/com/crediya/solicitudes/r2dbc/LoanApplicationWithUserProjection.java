package com.crediya.solicitudes.r2dbc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface LoanApplicationWithUserProjection {
    String getId();
    String getUserId();
    String getCustomerDocument();
    BigDecimal getAmount();
    Integer getTermMonths();
    String getEmail();
    String getLoanType();
    BigDecimal getInterestRate();
    BigDecimal getMonthlyPayment();
    String getStatus();
    LocalDateTime getCreatedAt();
    
    // Campos del usuario
    String getUserName();
    String getUserLastName();
    BigDecimal getUserBaseSalary();
}