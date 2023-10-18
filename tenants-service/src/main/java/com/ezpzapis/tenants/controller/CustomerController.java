package com.ezpzapis.tenants.controller;

import com.ezpzapis.commons.controller.GenericController;
import com.ezpzapis.tenants.model.Customer;
import com.ezpzapis.tenants.service.CustomerService;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Observed
@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController extends GenericController<Customer, CustomerService> {
    @Autowired
    public CustomerController(CustomerService service, SchemaGenerator schemaGenerator) {
        super(service, schemaGenerator);
    }
}
