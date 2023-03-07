package com.carlease.lease;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Car Lease API", version = "1.0", description = "Car Lease Service"))
public class CarLeaseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarLeaseServiceApplication.class, args);
	}

}
