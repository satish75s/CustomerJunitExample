package com.customer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.customer.entity.Customer;

import jakarta.transaction.Transactional;

/*@SpringBootTest
@Transactional
public class CustomerRepositoryTest {
	
	@Autowired
	private CustomerRepository customerRepository;

	Customer customer;

	 @BeforeEach
	    public void setUp() {
	        customer = new Customer();
	        customer.setName("John Doe");
	        customer.setEmail("john.doe@example.com");
	        customer = customerRepository.save(customer);
	    }
	@Test
	public void getCustomerById() {
		Customer customer = customerRepository.findById((long) 1).get();
		assertEquals("John Doe", customer.getName());
	}

}*/
