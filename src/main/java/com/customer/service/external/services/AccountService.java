package com.customer.service.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.customer.service.payload.ApiResponse;
import com.customer.service.entity.Account;

@FeignClient(name="ACCOUNT-MANAGEMENT-SERVICE")
public interface AccountService {
	
	@GetMapping("/accounts/customer/{customerId}")
	public List<Account> getCustomerAccounts(@PathVariable String customerId);
	
	@DeleteMapping("/accounts/{accountNumber}")
	public ResponseEntity<ApiResponse> deleteAccount(@PathVariable int accountNumber);

}
