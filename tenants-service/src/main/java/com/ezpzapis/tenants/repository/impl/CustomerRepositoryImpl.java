package com.ezpzapis.tenants.repository.impl;

import com.ezpzapis.commons.repository.impl.GenericRepositoryImpl;
import com.ezpzapis.tenants.model.Customer;
import com.ezpzapis.tenants.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepositoryImpl extends GenericRepositoryImpl<Customer> implements CustomerRepository {
    @Autowired
    public CustomerRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }
}
