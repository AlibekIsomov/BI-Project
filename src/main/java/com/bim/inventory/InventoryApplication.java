package com.bim.inventory;

import  org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InventoryApplication {


	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}


}
