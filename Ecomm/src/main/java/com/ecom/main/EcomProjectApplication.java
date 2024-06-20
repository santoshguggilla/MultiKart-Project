package com.ecom.main;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ecom.controller.ProductController;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.ecom.controller","com.ecom.service"})
@EntityScan("com.ecom.beans")
@EnableJpaRepositories("com.ecom.repository")
public class EcomProjectApplication {
	 private static final Logger logger = LogManager.getLogger(EcomProjectApplication.class);
	public static void main(String[] args) {
		
		
		SpringApplication.run(EcomProjectApplication.class, args);
		logger.info("Info level log message");
		logger.debug("Debug level log message");
		logger.error("Error level log message");
	}

}
