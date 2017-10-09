package com.github.chiby.ide.frontend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.chiby.player.DockerExecutor;

@Configuration
public class FrontendSpringConfiguration {

	@Bean
	DockerExecutor dockerExecutor(){
		return new DockerExecutor();
	}
}
