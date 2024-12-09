package com.web_app.personal_finance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleJwtException(IllegalStateException e) {
        return new ResponseEntity<>("Invalid JWT token", HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex,WebRequest request){
		
		ErrorResponse errorResponse = new ErrorResponse(
			"An unexpected error occurred",
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			request.getDescription(false).replace("uri=", "")
		); 
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
