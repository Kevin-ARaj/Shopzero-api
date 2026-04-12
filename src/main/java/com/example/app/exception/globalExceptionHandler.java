package com.example.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class globalExceptionHandler {
    
	@ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
	public org.springframework.http.ResponseEntity<?> handleValidation(
	        org.springframework.web.bind.MethodArgumentNotValidException ex) {

	    java.util.Map<String, String> errors = new java.util.HashMap<>();

	    ex.getBindingResult().getFieldErrors().forEach(error ->
	            errors.put(error.getField(), error.getDefaultMessage())
	    );

	    return new org.springframework.http.ResponseEntity<>(errors,
	            org.springframework.http.HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(resourceNotFoundException.class)
	public ResponseEntity<String> handleNotFound(resourceNotFoundException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntime(RuntimeException ex){
		Map<String,String> error = new HashMap<>();
		error.put("message",ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
}
