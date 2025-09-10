package com.crediya.solicitudes.r2dbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_requests")
@Builder(toBuilder = true)
public class LoanApplicationData {

    @Id
    private String id;

    @Column("user_id")
    private String customerDocument;

    private String email;

    @Column("customer_name")
    private String customerName;

    private BigDecimal amount;

    @Column("term")
    private Integer termMonths;

    @Column("loan_type")
    private String loanType;

    @Column("interest_rate")
    private BigDecimal interestRate;

    @Column("base_salary")
    private BigDecimal baseSalary;

    @Column("monthly_payment")
    private BigDecimal monthlyPayment;

    private String status;

    @Column("created_at")
    private LocalDateTime createdAt;
}