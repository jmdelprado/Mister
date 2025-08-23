package com.delprado.springboot.mister.springboot_mister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.delprado.springboot.mister.springboot_mister.entities")
@EnableJpaRepositories("com.delprado.springboot.mister.springboot_mister.repositories")
public class SpringbootMisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMisterApplication.class, args);
	}

}
