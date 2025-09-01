package com.crediya.solicitudes.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateLoanRequestRequest(
        @JsonProperty("user_id") @NotBlank String customerDocument,
        @JsonProperty("amount") @NotNull @DecimalMin("0.01") BigDecimal amount,
        @JsonProperty("term_months") @NotNull @Min(1) Integer termMonths,
        @JsonProperty("loan_type") @NotBlank String loanType
) {}