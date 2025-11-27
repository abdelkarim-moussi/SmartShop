package com.app.smartshop.application.exception;

public class ProductExistByNameException extends RuntimeException {
    public ProductExistByNameException(String message) {
        super(message);
    }
}
