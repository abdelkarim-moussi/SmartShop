package com.app.smartshop.infrastructure.exceptionHandler;

import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.EmailAleadyUsedException;
import com.app.smartshop.application.exception.ProductExistByNameException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotExistException.class)
    public ResponseEntity<ExceptionResponse> handleDataNotExistException(DataNotExistException exception, WebRequest request){
        ExceptionResponse response = ExceptionResponse.builder()
                .status(404)
                .error("data not found")
                .message(exception.getMessage())
                .path(request.getDescription(true))
                .build();

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(EmailAleadyUsedException.class)
    public ResponseEntity<ExceptionResponse> handleEmailAlreadyExistException(EmailAleadyUsedException exception, WebRequest request){
        ExceptionResponse response = ExceptionResponse.builder()
                .status(409)
                .error("email already exist")
                .message(exception.getMessage())
                .path(request.getDescription(true))
                .build();

        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(ProductExistByNameException.class)
    public ResponseEntity<ExceptionResponse> handleProductAlreadyExistByNameException(
            ProductExistByNameException exception,
            WebRequest request){

        ExceptionResponse response = ExceptionResponse.builder()
                .status(409)
                .error("product already exist")
                .message(exception.getMessage())
                .path(request.getDescription(true))
                .build();

        return ResponseEntity.status(409).body(response);
    }
}
