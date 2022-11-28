package com.audensiel.banking.exception;

public class InsufficientFundException extends RuntimeException{
    public InsufficientFundException() {
        super("Insufficient Fund");
    }
}
