package com.customer.service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.customer.service.entity.Account;
import com.customer.service.entity.Customer;
import com.customer.service.exceptions.CustomDatabaseException;
import com.customer.service.exceptions.ResourceNotFoundException;
import com.customer.service.external.services.AccountService;
import com.customer.service.repository.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	private Logger logger=LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	
	//USING REST TEMPLATE 
	//1st METHOD , create bean in Config/AppConfig.class for this to be autowired
//	@Autowired
//	private RestTemplate restTemplate;
	
	//2nd METHOD , use of RestTemplateBuilder
//	private final RestTemplate restTemplate;
//	
//	public CustomerServiceImpl(RestTemplateBuilder restTemplateBuilder) {
//		this.restTemplate = restTemplateBuilder.build();
//	}
	
	//USING FEIGN CLIENT
	@Autowired
	private AccountService accountService;
	
	// GET SINGLE CUSTOMER AND SET ALL RESPECTIVE ACCOUNTS
	@Override
	public Customer getCustomer(String customerId) {
		Optional<Customer> existingCustomer = customerRepository.findById(customerId);
		if (existingCustomer.isPresent()) {
			Customer customer= existingCustomer.get();
			// CALLING ACCOUNT LIST FOR CUSTOMER USING REST TEMPLATE
//			String url="http://ACCOUNT_MANAGEMENT_SERVICE/account/customer/"+customerId;
//			List<Account> accountList=restTemplate.getForObject(url,ArrayList.class);
			
			// CALLING ACCOUNT LIST FOR CUSTOMER USING FEIGN CLIENT
			List<Account> accountList=accountService.getCustomerAccounts(customerId);
			logger.info("{}",accountList);
			customer.setAccounts(accountList);
			return customer;
	    } else {
	        throw new ResourceNotFoundException("Customer not found with id: " + customerId);
	    }
	}
	
	// CREATE NEW CUSTOMER
	@Override
	public Customer addCustomer(Customer customer) {
		try {
	        return customerRepository.save(customer);
	    } catch (DataIntegrityViolationException e) {
	        throw new CustomDatabaseException("Constraint violation - possibly duplicate entry", e);
	    } catch (Exception e) {
	        throw new CustomDatabaseException("An error occurred while adding the customer", e);
	    }
	}
	
	// GET ALL CUSTOMERS AND SET ALL ACCOUNTS
	@Override
	public List<Customer> getAllCustomers() {
		try {
			// CALLING ACCOUNT LIST FOR EACH CUSTOMER	
	        List<Customer> allCustomers=customerRepository.findAll();
	        for (Customer customer : allCustomers) {
//	        	List<Account> accountList=restTemplate.getForObject("http://localhost:8082/account/customer/"+customer.getCustomerId(),ArrayList.class);
	        	List<Account> accountList=accountService.getCustomerAccounts(customer.getCustomerId());
	        	customer.setAccounts(accountList);
			}
	        return allCustomers;
	    } catch (Exception e) {
	        throw new CustomDatabaseException("An error occurred while retrieving customers", e);
	    }
		
	}

	
	// UPDATE THE CUSTOMER DETAILS
	@Override
	public Customer updateCustomer(Customer customer) {
		Optional<Customer> existingCustomer = customerRepository.findById(customer.getCustomerId());
	    if (existingCustomer.isPresent()) {
	    	return customerRepository.save(customer);
	    } else {
	        throw new ResourceNotFoundException("Customer not found with id: " + customer.getCustomerId());
	    }
	}
	
	// DELETE CUSTOMER IT ALL IT's ACCOUNT
	@Override
	public Customer deleteCustomer(String customerId) {
		Optional<Customer> existingCustomer=customerRepository.findById(customerId);
		
		if(existingCustomer.isPresent()) {
			// FETCH ALL ACCOUNTS OF CUSTOMER AND DELETE THEM
			List<Account> accountList=accountService.getCustomerAccounts(customerId);
			for (Account account : accountList) {
				accountService.deleteAccount(account.getAccountNumber());
			}
			// DELETE THE CUSTOMER DETAIL
			customerRepository.deleteById(customerId);
			return existingCustomer.get();
		}else {
			throw new ResourceNotFoundException("Customer not found with id "+customerId);
		} 
	}

}
