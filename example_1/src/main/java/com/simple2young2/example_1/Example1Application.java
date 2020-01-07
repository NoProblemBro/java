package com.simple2young2.example_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;


/**
 * example with circuit breaker
 * 
 * @author LeonZhong
 */

@EnableCircuitBreaker
@SpringBootApplication
public class Example1Application extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(Example1Application.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Example1Application.class);
    }

}
