package com.smartwf.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.smartwf.auth.*.dao"})
@ComponentScan(basePackages = {"com.smartwf"})
public class AuthServerApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(AuthServerApplication.class, args);
	}

}
