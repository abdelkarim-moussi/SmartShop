package com.app.smartshop.application.exception;

public class EmailAleadyUsedException extends RuntimeException {
    public EmailAleadyUsedException(String message) {
        super(message);
    }
}
