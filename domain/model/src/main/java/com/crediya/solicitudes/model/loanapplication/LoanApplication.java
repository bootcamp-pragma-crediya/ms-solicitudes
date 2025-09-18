package com.crediya.solicitudes.model.loanapplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanApplication {
    String id;
    String userId;
    String customerDocument;
    String email;
    String customerName;
    BigDecimal amount;
    Integer termMonths;
    String loanType;
    BigDecimal interestRate;
    BigDecimal baseSalary;
    BigDecimal monthlyPayment;
    LoanStatus status;
    OffsetDateTime createdAt;
}