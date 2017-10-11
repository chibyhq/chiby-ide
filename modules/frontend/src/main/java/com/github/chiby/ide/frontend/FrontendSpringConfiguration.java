package com.github.chiby.ide.frontend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.chiby.player.DockerExecutor;
import com.github.chiby.player.PygamezeroExecutor;

@Configuration
public class FrontendSpringConfiguration {

	@Bean
	DockerExecutor dockerExecutor(){
		return new DockerExecutor();
	}
	
	@Bean
	PygamezeroExecutor pygamezeroExecutor(){
		return new PygamezeroExecutor();
	}
}
