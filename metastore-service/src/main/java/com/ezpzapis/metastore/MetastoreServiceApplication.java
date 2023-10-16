package com.ezpzapis.metastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@SpringBootApplication
public class MetastoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetastoreServiceApplication.class, args);
	}

	@GetMapping("/")
	public Mono<String> home() {
		return Mono.just("Metastore Service");
	}

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http.authorizeExchange().anyExchange().permitAll().and().build();
	}
}
