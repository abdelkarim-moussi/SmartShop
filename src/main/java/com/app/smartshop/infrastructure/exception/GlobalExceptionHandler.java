package com.app.smartshop.infrastructure.exception;

import com.app.smartshop.application.exception.DataNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotExistException.class)
    public ResponseEntity<?> handleDataNotExistException(DataNotExistException exception, WebRequest request){
        ExceptionResponse response = ExceptionResponse.builder()
                .status(404)
                .error("data not found")
                .message(exception.getMessage())
                .path(request.getDescription(true))
                .build();

        return ResponseEntity.status(404).body(response);
    }
}
