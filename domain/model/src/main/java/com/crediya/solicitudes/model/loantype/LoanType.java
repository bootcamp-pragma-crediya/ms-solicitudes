package com.crediya.solicitudes.model.loantype;
import lombok.*;


@Value
@Builder
public class LoanType {
    String code;
    String name;
    boolean active;
}