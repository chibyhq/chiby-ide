package com.github.chiby.ide.frontend;

import java.util.logging.Level;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.map.repository.config.EnableMapRepositories;

import com.github.chiby.ide.frontend.desktop.IdeTrayIcon;

import lombok.extern.java.Log;

@SpringBootApplication
@EnableMapRepositories("com.github.chibyhq.store.model.repositories")
@Log
public class FrontendApplication {
	private static ConfigurableApplicationContext s_context;
	
	private static IdeTrayIcon s_trayIcon;
	
	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(FrontendApplication.class);

		// By default, we assume a desktop environment 
		boolean headless = System.getProperty("chiby.ide.headless", "false").equalsIgnoreCase("true");
		
		log.log(Level.INFO, "Running in headless mode ? {0}",headless);
		
        builder.headless(headless);
        s_context = builder.run(args);
        
        if(!headless) {
          s_trayIcon = new IdeTrayIcon();
        }
	}

	public static ConfigurableApplicationContext getContext() {
		return s_context;
	}
	
	public static IdeTrayIcon getTrayIcon() {
		return s_trayIcon;
	}

}
