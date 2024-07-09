package com.customer.service.exceptions;

//The ResourceNotFoundException class extends RuntimeException and is a custom exception designed to 
//handle cases where a requested resource is not found on the server. 
public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(){
		super("Resource not found on server ");
	}
	
	public ResourceNotFoundException(String errorMessage) {
		super(errorMessage);
		
	}

}
