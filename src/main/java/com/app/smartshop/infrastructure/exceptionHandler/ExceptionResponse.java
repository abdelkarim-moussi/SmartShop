package com.app.smartshop.infrastructure.exceptionHandler;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ExceptionResponse {
    int status;
    String error;
    String message;
    String path;
    LocalDateTime timestamp = LocalDateTime.now();
}
