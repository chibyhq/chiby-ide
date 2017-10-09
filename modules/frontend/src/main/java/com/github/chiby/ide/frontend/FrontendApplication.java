package com.github.chiby.ide.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableMapRepositories("com.github.chiby.store.model.repositories")
@EnableSpringDataWebSupport
public class FrontendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}
}
