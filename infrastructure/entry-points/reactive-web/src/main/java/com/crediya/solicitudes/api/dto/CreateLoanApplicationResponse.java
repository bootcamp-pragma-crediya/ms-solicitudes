package com.crediya.solicitudes.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateLoanApplicationResponse(
        @JsonProperty("id") String id,
        @JsonProperty("email") String email,
        @JsonProperty("user_id") String userId,
        @JsonProperty("documento_cliente") String customerDocument,
        @JsonProperty("monto") BigDecimal amount,
        @JsonProperty("plazo") Integer termMonths,
        @JsonProperty("tipo_prestamo") String loanType,
        @JsonProperty("estado_solicitud") String status
) {}