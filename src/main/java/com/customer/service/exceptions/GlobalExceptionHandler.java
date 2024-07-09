package com.customer.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.customer.service.payload.ApiResponse;


// @RestControllerAdvice is used to handle exceptions globally for all RESTful controllers in a Spring Boot application.
//It allows you to handle exceptions across the whole application in one global handling component,
//rather than having to define exception handlers in each controller.

//@ExceptionHandler: This annotation is used to define the method that handles specific exceptions.
//ResourceNotFoundException.class: This specifies that the method will handle ResourceNotFoundException.

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){
		String message=ex.getMessage();
		
		ApiResponse response=ApiResponse.builder().message(message).success(false).status(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(CustomDatabaseException.class)
	public ResponseEntity<ApiResponse> handlerCustomDatabaseException(CustomDatabaseException ex){
		String message=ex.getMessage();
		
		ApiResponse response=ApiResponse.builder().message(message).success(false).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
//ApiResponse is a custom class designed to standardize the structure of responses sent by your API, 
//especially when handling exceptions. Using a standard response format helps ensure consistency and clarity in the API responses,
//making it easier for clients to parse and understand the responses.