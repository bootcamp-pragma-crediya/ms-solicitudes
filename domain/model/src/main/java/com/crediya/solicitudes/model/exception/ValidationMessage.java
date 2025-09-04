package com.crediya.solicitudes.model.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ValidationMessage {
    BODY_REQUIRED("Body is required"),
    CUSTOMER_DOCUMENT_REQUIRED("customerDocument is required"),
    AMOUNT_MUST_BE_POSITIVE("amount must be > 0"),
    TERM_MONTHS_MUST_BE_POSITIVE("termMonths must be > 0"),
    LOAN_TYPE_REQUIRED("loanType is required"),
    LOAN_TYPE_INVALID("loanType is invalid or inactive");

    private final String message;
}