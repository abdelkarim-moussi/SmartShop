package com.app.smartshop.infrastructure.exception;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ExceptionResponse {
    LocalDateTime timestamp;
    String error;
    String message;
    String path;
    int status;
}
