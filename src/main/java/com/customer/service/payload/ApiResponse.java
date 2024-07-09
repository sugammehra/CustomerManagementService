package com.customer.service.payload;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Lombok is a Java library that helps reduce boilerplate code in Java applications by automatically
//generating commonly used methods such as getters, setters, equals, hashCode, toString, and constructors. 

// @Data: A shorthand for @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor.
@Getter  // Automatically generates getter and setter methods for all fields.
@Setter
@AllArgsConstructor  //Generates a constructor with one argument for each field.
@NoArgsConstructor  //Generates a no-argument constructor.
@Builder
public class ApiResponse {
	private String message;
	private boolean success;
	private HttpStatus status;
}
