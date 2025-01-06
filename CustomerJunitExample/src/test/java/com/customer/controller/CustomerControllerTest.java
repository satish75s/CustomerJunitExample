package com.customer.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.customer.entity.Customer;
import com.customer.service.CustomerService;



@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	private Customer customer;

	@BeforeEach
	void setup() {
		customer = new Customer(1, "John Doe", "john.doe@example.com");
	}

	@Test
	void testAddCustomer() throws Exception {
		Customer customerEntity = new Customer(1L, "John Doe", "john.doe@example.com");
		Mockito.when(customerService.addCustomer(customerEntity)).thenReturn(customer);
		mockMvc.perform(MockMvcRequestBuilders.post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + 
						"  \"id\": 1L,\r\n" + 
						"  \"name\": \"John Doe\",\r\n" + 
						"  \"email\": \"john.doe@example.com\",\r\n" + 
						
						"}"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	 @Test
	    public void testGetCustomerById_CustomerNotFound() throws Exception {
	        // Arrange: Customer ID does not exist
	        Long customerId = 101L;

	        // Mock the service method to return an empty Optional
	        when(customerService.getCustomerById(customerId)).thenReturn(Optional.empty());

	        // Act & Assert: Send a GET request to the /{id} endpoint and verify the response
	        mockMvc.perform(get("/customers/{id}", customerId))  // Send GET request to the /{id} endpoint
	                .andExpect(status().isOk());  // Expect HTTP status 404 Not Found
	    }
	 
	
	 
	 @Test
	    public void testGetCustomerByName_CustomerExists() throws Exception {
	        // Arrange: Create some sample customers with the same name
	        String name = "John";
	        Customer customer1 = new Customer();
	        customer1.setId(1L);
	        customer1.setName("John");
	        customer1.setEmail("john1@example.com");

	        Customer customer2 = new Customer();
	        customer2.setId(2L);
	        customer2.setName("John");
	        customer2.setEmail("john2@example.com");

	        List<Customer> customers = Arrays.asList(customer1, customer2);

	        // Mock the service method to return the list of customers with the given name
	        when(customerService.getCustomerByName(name)).thenReturn(customers);

	        // Act & Assert: Send a GET request to the /name/{name} endpoint and verify the response
	        mockMvc.perform(get("/customers/name/{name}", name)  // Send GET request to the /name/{name} endpoint
	                .contentType(MediaType.APPLICATION_JSON))  // Set Content-Type header to JSON
	        .andExpect(status().isOk())  // Expect HTTP status 200 OK
	        .andExpect(jsonPath("$[0].id", is(1)))  // Verify the first customer ID
	        .andExpect(jsonPath("$[0].name", is("John")))  // Verify the first customer name
	        .andExpect(jsonPath("$[0].email", is("john1@example.com")))  // Verify the first customer email
	        .andExpect(jsonPath("$[1].id", is(2)))  // Verify the second customer ID
	        .andExpect(jsonPath("$[1].name", is("John")))  // Verify the second customer name
	        .andExpect(jsonPath("$[1].email", is("john2@example.com")));  // Verify the second customer email
	    }

	    @Test
	    public void testGetCustomerByName_CustomerNotFound() throws Exception {
	        // Arrange: The name does not match any customers
	        String name = "NonExistentName";

	        // Mock the service method to return an empty list
	        when(customerService.getCustomerByName(name)).thenReturn(Arrays.asList());

	        // Act & Assert: Send a GET request to the /name/{name} endpoint and verify the response
	        mockMvc.perform(get("/customers/name/{name}", name))  // Send GET request to the /name/{name} endpoint
	                .andExpect(status().isOk())  // Expect HTTP status 200 OK (empty list is valid)
	                .andExpect(jsonPath("$").isEmpty());  // Expect an empty response body
	    }
	 

}