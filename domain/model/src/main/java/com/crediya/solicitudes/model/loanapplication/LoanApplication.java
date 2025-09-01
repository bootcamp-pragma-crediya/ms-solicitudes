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
    String customerDocument;
    BigDecimal amount;
    Integer termMonths;
    String loanType;
    LoanStatus status;
    OffsetDateTime createdAt;
}