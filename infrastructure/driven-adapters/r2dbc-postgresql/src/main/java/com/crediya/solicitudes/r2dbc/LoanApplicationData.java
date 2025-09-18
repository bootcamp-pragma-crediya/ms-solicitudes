package com.crediya.solicitudes.r2dbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_requests")
@Builder(toBuilder = true)
public class LoanApplicationData implements Persistable<String> {

    @Id
    private String id;

    @Column("user_id")
    private String userId;

    @Column("customer_document")
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

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;
    
    @Transient
    @Builder.Default
    private boolean isNew = true;
    
    @Override
    public boolean isNew() {
        return isNew;
    }
    
    public LoanApplicationData markNotNew() {
        this.isNew = false;
        return this;
    }
}