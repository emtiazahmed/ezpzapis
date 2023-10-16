package com.ezpzapis.tenants.service.impl;

import com.ezpzapis.commons.service.impl.GenericServiceImpl;
import com.ezpzapis.tenants.model.Customer;
import com.ezpzapis.tenants.repository.CustomerRepository;
import com.ezpzapis.tenants.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends GenericServiceImpl<Customer, CustomerRepository> implements CustomerService {
    @Autowired
    public CustomerServiceImpl(CustomerRepository repository, ObjectMapper objectMapper) {
        super(repository, objectMapper);
    }
}
