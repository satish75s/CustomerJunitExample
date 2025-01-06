package com.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.customer.entity.Customer;
import com.customer.repository.CustomerRepository;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1, "John Doe", "john.doe@example.com");
    }

    @Test
    public void testAddCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.addCustomer(customer);
        assertNotNull(savedCustomer);
        assertEquals("John Doe", savedCustomer.getName());
        assertEquals("john.doe@example.com", savedCustomer.getEmail());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> foundCustomer = customerService.getCustomerById(1L);
        assertTrue(foundCustomer.isPresent());
        assertEquals("John Doe", foundCustomer.get().getName());

        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCustomerByName() {
        when(customerRepository.findByName("John Doe")).thenReturn(List.of(customer));

        List<Customer> customers = customerService.getCustomerByName("John Doe");
        assertFalse(customers.isEmpty());
        assertEquals("John Doe", customers.get(0).getName());

        verify(customerRepository, times(1)).findByName("John Doe");
    }

    @Test
    public void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }
    @Test
    void testGetAllCustomers() {
        // Arrange
        Customer customer1 = new Customer(1, "John Doe", "johndoe@example.com");
        Customer customer2 = new Customer(2, "Jane Smith", "janesmith@example.com");

        // Mock the repository behavior
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        // Act
        List<Customer> result = customerService.getAllCustomers();

        // Assert
        assertEquals(2, result.size()); // Verify that the result contains 2 customers
        assertEquals("John Doe", result.get(0).getName()); // Check the first customer's name
        assertEquals("Jane Smith", result.get(1).getName()); // Check the second customer's name
    }
}
