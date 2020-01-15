package com.github.chiby.ide.frontend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.map.repository.config.EnableMapRepositories;

import com.github.chiby.ide.frontend.desktop.IdeTrayIcon;

@SpringBootApplication
@EnableMapRepositories("com.github.chibyhq.store.model.repositories")
public class FrontendApplication {
	private static ConfigurableApplicationContext s_context;
	
	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(FrontendApplication.class);
        builder.headless(false);
        s_context = builder.run(args);
        
        IdeTrayIcon tray = new IdeTrayIcon();
	}

	public static ConfigurableApplicationContext getContext() {
		return s_context;
	}

}
