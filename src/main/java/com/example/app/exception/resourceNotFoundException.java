package com.example.app.exception;

public class resourceNotFoundException extends RuntimeException {
	public resourceNotFoundException(String message) {
		super(message);
	}
}
