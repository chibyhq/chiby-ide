package com.github.chiby.ide.frontend;

import static org.mockito.Mockito.*;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.chiby.ide.frontend.util.ApplicationHomeResolver;
import com.github.chiby.player.model.Application;
import com.github.chiby.player.model.ApplicationTypeConstants;
import com.github.chiby.player.model.ApplicationTypeEnum;
import com.github.chiby.store.model.repositories.ApplicationRepository;
import com.google.common.jimfs.Jimfs;

@RunWith(MockitoJUnitRunner.class)
public class ChibyHomeDataInitializerTest {

	
	@Mock
	ApplicationRepository applicationRepository;
	
	/**
	 * Simulate a simple file system containing application definition files
	 */
	@Test
	public void testSimpleHome() throws Exception {
		ChibyHomeDataInitializer chdi = new ChibyHomeDataInitializer();
		chdi.frontendConfig = FrontendConfigProperties.builder().home("/projects").build();
		FileSystem fs = Jimfs.newFileSystem();
		UUID uuid = UUID.randomUUID();
		Path projectHome = fs.getPath("/projects/"+uuid.toString());
		Files.createDirectories(projectHome);
		
		Application app = Application.builder().uuid(uuid).title("App title 1").type(ApplicationTypeEnum.PYGAMEZERO).build();
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.writeValue(Files.newOutputStream(projectHome.resolve(ApplicationTypeConstants.APPLICATION_YAML_FILE)), app);
		mapper.writeValue(System.out, app);
		
		chdi.fileSystem = fs;
		chdi.applicationRepository = applicationRepository;
		
		chdi.run(null);
		
		verify(applicationRepository).save(app);
		
	}
	/**
	 * Simulate a simple file system containing application definition files
	 */
	@Test
	public void testHomeInitialization() throws Exception {
		ChibyHomeDataInitializer chdi = new ChibyHomeDataInitializer();
		chdi.frontendConfig = FrontendConfigProperties.builder().home("/projects").initializeHome(true).build();
		FileSystem fs = Jimfs.newFileSystem();
		
		chdi.applicationHomeResolver = new ApplicationHomeResolver();
		chdi.applicationHomeResolver.setConfig(chdi.frontendConfig);
		chdi.applicationHomeResolver.setFileSystem(fs);
		chdi.fileSystem = fs;
		chdi.applicationRepository = applicationRepository;
		
		chdi.run(null);
		
		// Check that all sample project templates are added to the home
		verify(applicationRepository, times(3)).save((Application)any());
	}

}
