package com.crediya.solicitudes.model.exception;

public class InvalidLoanTypeException extends RuntimeException{
    public InvalidLoanTypeException(String message){
        super(message);
    }
}
