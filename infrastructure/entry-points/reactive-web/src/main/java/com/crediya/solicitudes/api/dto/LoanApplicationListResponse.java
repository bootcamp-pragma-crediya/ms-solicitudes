package com.crediya.solicitudes.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record LoanApplicationListResponse(
    String id,
    @JsonProperty("monto") BigDecimal amount,
    @JsonProperty("plazo") Integer termMonths,
    String email,
    @JsonProperty("nombre") String customerName,
    @JsonProperty("tipo_prestamo") String loanType,
    @JsonProperty("tasa_interes") BigDecimal interestRate,
    @JsonProperty("estado_solicitud") String status,
    @JsonProperty("salario_base") BigDecimal baseSalary,
    @JsonProperty("monto_mensual_solicitud") BigDecimal monthlyPayment
) {
}