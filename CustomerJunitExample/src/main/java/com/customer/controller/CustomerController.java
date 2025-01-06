package com.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.entity.Customer;
import com.customer.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
    	log.info("addCustomer begins in controller");
        return customerService.addCustomer(customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
    	log.info("fetching customer details using id in controller");
        return customerService.getCustomerById(id).orElse(null);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
    	log.info("fetching all customer details in controller");
        return customerService.getAllCustomers();
    }

    @GetMapping("/name/{name}")
    public List<Customer> getCustomerByName(@PathVariable String name) {
    	log.info("fetching customer details using name in controller");
        return customerService.getCustomerByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
