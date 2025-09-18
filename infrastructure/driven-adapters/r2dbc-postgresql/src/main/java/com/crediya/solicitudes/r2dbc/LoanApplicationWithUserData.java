package com.crediya.solicitudes.r2dbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationWithUserData {
    private String id;
    private String userId;
    private String customerDocument;
    private BigDecimal amount;
    private Integer termMonths;
    private String email;
    private String loanType;
    private BigDecimal interestRate;
    private BigDecimal monthlyPayment;
    private String status;
    private LocalDateTime createdAt;
    
    // Campos del usuario
    private String userName;
    private String userLastName;
    private BigDecimal userBaseSalary;
}