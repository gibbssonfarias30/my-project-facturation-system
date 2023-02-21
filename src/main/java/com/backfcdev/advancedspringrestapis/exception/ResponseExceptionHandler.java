package com.backfcdev.advancedspringrestapis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ModelNotFoundException.class)
    ResponseEntity<CustomErrorResponse> handleProductNotFoundException(ModelNotFoundException ex){
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND, LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(StorageException.class)
    ResponseEntity<CustomErrorResponse> handleStorageException(StorageException ex){
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND, LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }
}
