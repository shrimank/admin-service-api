package com.platform.adminserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.platform.domain")
@ComponentScan({ "com.evolvus.repository", "com.evolvus.controller" })
@EnableAutoConfiguration
public class AdminServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminServiceApiApplication.class, args);
	}
}
