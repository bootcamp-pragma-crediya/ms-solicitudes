package com.crediya.solicitudes.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationListResponse {
    private String id;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("documento_cliente")
    private String customerDocument;
    @JsonProperty("monto")
    private BigDecimal amount;
    @JsonProperty("plazo")
    private Integer termMonths;
    private String email;
    @JsonProperty("nombre")
    private String customerName;
    @JsonProperty("tipo_prestamo")
    private String loanType;
    @JsonProperty("estado_solicitud")
    private String status;
    @JsonProperty("salario_base")
    private BigDecimal baseSalary;
}