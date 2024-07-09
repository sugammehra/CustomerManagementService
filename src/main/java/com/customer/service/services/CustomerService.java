package com.customer.service.services;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.customer.service.entity.Customer;

@Repository
public interface CustomerService {
	
	//add Customer
	Customer addCustomer(Customer customer);
	
	//Get All Customers
	List<Customer>  getAllCustomers();
	
	//Get single Customer
	Customer getCustomer(String customerId);
	
	//Update Customer Details
	Customer updateCustomer(Customer customer);
	
	//Delete Customer
	Customer deleteCustomer(String customerId);

}
