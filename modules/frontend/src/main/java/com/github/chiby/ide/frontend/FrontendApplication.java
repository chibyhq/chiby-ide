package com.github.chiby.ide.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@SpringBootApplication
@EnableMapRepositories("com.github.chibyhq.store.model.repositories")
public class FrontendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}
}
