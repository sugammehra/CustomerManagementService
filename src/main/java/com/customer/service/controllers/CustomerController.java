package com.customer.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.service.entity.Customer;
import com.customer.service.payload.ApiResponse;
import com.customer.service.services.CustomerService;

@RestController
@RequestMapping("/customers")  // if /customers fire up , this controller class gets loaded
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	// ADD NEW CUSTOMER
	@PostMapping
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
		Customer newCustomer = customerService.addCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
	}
	
	//GET SINGLE CUSTOMER
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getSingleCustomer(@PathVariable String customerId){
		Customer existingCustomer=customerService.getCustomer(customerId);
		return ResponseEntity.ok(existingCustomer);
	}
	
	//GET ALL CUSTOMERS
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers(){
		List<Customer> allCustomers=customerService.getAllCustomers();
		return ResponseEntity.ok(allCustomers);
	}
	
	// UPDATE THE DETAILS OF CUSTOMER
	@PutMapping("/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable String customerId, @RequestBody Customer customer) {
	    customer.setCustomerId(customerId);
	    Customer updatedCustomer = customerService.updateCustomer(customer);
	    return ResponseEntity.ok(updatedCustomer);
	}
	
	// DELETE THE CUSTOMER
	@DeleteMapping("/{customerId}")
	public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable String customerId){
		
		customerService.deleteCustomer(customerId);
        ApiResponse response = ApiResponse.builder()
                .message("Customer with ID " + customerId + " has been deleted successfully.")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	// if we want to receive message after delete request , we can use api response class that we have build. 
	// otherwise we can simply return ResponseEntity.noContent().build();
	
	

}
