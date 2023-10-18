package com.ezpzapis.tenants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@SpringBootApplication
@EnableReactiveMongoRepositories
public class TenantsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenantsServiceApplication.class, args);
    }


}
