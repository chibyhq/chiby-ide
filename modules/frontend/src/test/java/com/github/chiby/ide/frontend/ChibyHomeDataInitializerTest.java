package com.github.chiby.ide.frontend;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.yaml.snakeyaml.Yaml;

import com.github.chiby.player.model.Application;
import com.github.chiby.store.model.repositories.ApplicationRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Jimfs;

import static org.mockito.Mockito.*;

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
		Yaml yaml = new Yaml();
		chdi.frontendConfig = FrontendConfig.builder().home("/projects").build();
		FileSystem fs = Jimfs.newFileSystem();
		Path projectHome = fs.getPath("/projects/app1");
		Files.createDirectories(projectHome);
		
		Application app = Application.builder().title("App title 1").build();
		Files.write(projectHome.resolve(".application.yml"), ImmutableList.of(yaml.dump(app)), StandardCharsets.UTF_8);
		
		chdi.fileSystem = fs;
		chdi.applicationRepository = applicationRepository;
		
		chdi.run(null);
		
		verify(applicationRepository).save(app);
		
	}

}
