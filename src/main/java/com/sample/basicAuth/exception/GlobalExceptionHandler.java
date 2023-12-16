package com.sample.basicAuth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> exception(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }


    @ExceptionHandler
    public ResponseEntity<?> customException(UserCustomException customException) {
        return ResponseEntity.badRequest().body(customException.getMessage());
    }
}
