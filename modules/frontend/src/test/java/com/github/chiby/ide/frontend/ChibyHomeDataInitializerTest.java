package com.github.chiby.ide.frontend;

import static org.mockito.Mockito.verify;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
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
		chdi.frontendConfig = FrontendConfig.builder().home("/projects").build();
		FileSystem fs = Jimfs.newFileSystem();
		UUID uuid = UUID.randomUUID();
		Path projectHome = fs.getPath("/projects/"+uuid.toString());
		Files.createDirectories(projectHome);
		
		Application app = Application.builder().uuid(uuid).title("App title 1").build();
		app.setType(ApplicationTypeEnum.PYGAMEZERO);
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.writeValue(Files.newOutputStream(projectHome.resolve(ApplicationTypeConstants.APPLICATION_YAML_FILE)), app);
		
		chdi.fileSystem = fs;
		chdi.applicationRepository = applicationRepository;
		
		chdi.run(null);
		
		verify(applicationRepository).save(app);
		
	}

}
