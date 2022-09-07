package com.smartwf.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.smartwf.client"})
public class ClientMainApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ClientMainApplication.class, args);
	}

}
