package com.customer.service.entity;
import lombok.Data;

@Data
public class Account {
	private Integer accountNumber;
	
	private double balance;
	
	private String accountHolderName;
	
	private String customerId;
}

//Using Integer instead of int is preferable in this case because it allows for the possibility of representing a null value.
//Using int would imply that the field must always have a value, whereas using Integer allows the field to be nullable if needed.
