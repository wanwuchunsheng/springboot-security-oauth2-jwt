package com.smartwf.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * 启动类
 * @author WCH
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.smartwf.web.*.*.dao"})
@ComponentScan(basePackages = {"com.smartwf.security","com.smartwf.web"})
public class WebMainApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WebMainApplication.class, args);
		
	}
}
