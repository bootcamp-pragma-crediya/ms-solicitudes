package com.crediya.solicitudes.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record LoanApplicationResponse(
        @JsonProperty("id") String id,
        @JsonProperty("user_id") String userId,
        @JsonProperty("status") String status,
        @JsonProperty("customer_document") String customerDocument,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("term_months") Integer termMonths,
        @JsonProperty("loan_type") String loanType,
        @JsonProperty("created_at") OffsetDateTime createdAt
) {}