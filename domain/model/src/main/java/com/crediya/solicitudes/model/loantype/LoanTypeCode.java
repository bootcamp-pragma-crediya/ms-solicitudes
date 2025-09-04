package com.crediya.solicitudes.model.loantype;

public enum LoanTypeCode {
    PERSONAL("PERSONAL"),
    MORTGAGE("MORTGAGE"),
    AUTO("AUTO"),
    BUSINESS("BUSINESS");

    private final String code;

    LoanTypeCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}