package com.web_app.personal_finance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleJwtException(IllegalStateException e) {
        return new ResponseEntity<>("Invalid JWT token", HttpStatus.UNAUTHORIZED);
    }
	
}
