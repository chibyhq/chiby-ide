package com.github.chiby.ide.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import com.github.chiby.store.model.repositories.ApplicationRepository;

/**
 * This initializer class inspects all project folders on the file system
 * and populates the repositories.
 * @author bcopy
 *
 */
public class ChibyHomeDataInitializer implements ApplicationRunner{

	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	FrontendConfig frontendConfig;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		// Look into each project folder for an application.yml file
		// to deserialize
		frontendConfig.getHome();
	}
	
}
