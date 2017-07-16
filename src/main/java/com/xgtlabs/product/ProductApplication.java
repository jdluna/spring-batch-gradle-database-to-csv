package com.xgtlabs.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * lancement du job via springBoot
 * 
 * @author javadev
 *
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ProductApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}
	
}
