package com.exercise.bankaccounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BankAccountNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
