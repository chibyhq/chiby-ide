package com.github.chiby.ide.frontend;

import static com.github.chiby.player.model.ApplicationTypeEnum.PYGAMEZERO;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.chiby.player.model.Application;
import com.github.chiby.player.model.ApplicationTypeConstants;
import com.github.chiby.store.model.repositories.ApplicationRepository;
import com.google.common.jimfs.Jimfs;

@RunWith(MockitoJUnitRunner.class)
public class ChibyHomeApplicationSerializerTest {
	@Mock
	ApplicationRepository applicationRepository;

	@Test
	public void test() throws IOException {
		ChibyHomeApplicationSerializer chds = new ChibyHomeApplicationSerializer();
		chds.config = FrontendConfig.builder().home("/projects").build();
		FileSystem fs = Jimfs.newFileSystem();
		UUID uuid = UUID.randomUUID();
		Path projectHome = fs.getPath("/projects");
		Files.createDirectories(projectHome);
		
		Application app = Application.builder().uuid(uuid).title("App title 1").type(PYGAMEZERO).build();
		app.setGeneratedContents("generated");
		app.setContents("contents");
		
		chds.fileSystem = fs;
		chds.repository = applicationRepository;
		
		when(applicationRepository.findAll()).thenReturn(Arrays.asList(new Application[]{app}));
		
		chds.persistAllApplications();
		
		// Assert that required files have been persisted
		Path appHome = projectHome.resolve(uuid.toString());
		assertTrue(Files.exists(appHome));
		assertTrue(Files.exists(appHome.resolve(ApplicationTypeConstants.APPLICATION_YAML_FILE)));
		assertTrue(Files.exists(appHome.resolve(ApplicationTypeConstants.BLOCKLY_WORKSPACE_XML)));
		assertTrue(Files.exists(appHome.resolve(ApplicationTypeConstants.PYTHON_APPLICATION_PY)));

		// Check that the contents and generated contents are not present in the Application YAML file
		String appYaml = new String(Files.readAllBytes(appHome.resolve(ApplicationTypeConstants.APPLICATION_YAML_FILE)));
		
		assertTrue(!appYaml.contains("generated"));
		assertTrue(!appYaml.contains("contents"));
	}

}