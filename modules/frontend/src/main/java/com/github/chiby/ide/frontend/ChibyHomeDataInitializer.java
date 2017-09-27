package com.github.chiby.ide.frontend;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.chiby.player.model.Application;
import com.github.chiby.player.model.ApplicationTypeConstants;
import com.github.chiby.store.model.repositories.ApplicationRepository;

import lombok.extern.java.Log;

/**
 * This initializer class inspects all project folders on the file system and
 * populates the repositories.
 * 
 * @author bcopy
 *
 */
@Log
public class ChibyHomeDataInitializer implements ApplicationRunner {

	private static final String APPLICATION_YML = ".application.yml";

	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	FrontendConfig frontendConfig;

	FileSystem fileSystem = FileSystems.getDefault();

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		// Look into each project folder for an application.yml file
		// to deserialize
		Path homePath = fileSystem.getPath(frontendConfig.getHome());
		if (Files.isDirectory(homePath)) {
			Files.newDirectoryStream(fileSystem.getPath(frontendConfig.getHome()))
					.forEach(this::findApplicationDefinition);
		}
	}

	private void findApplicationDefinition(Path applicationHome){
		try{
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			Path applicationYamlPath = applicationHome.resolve(APPLICATION_YML);
			
			Application app = mapper.readValue(Files.newInputStream(applicationYamlPath), Application.class);
			
			switch(app.getType()){
			case PYGAMEZERO:
			case PYTHON:
				Path blocklyWorkspace = applicationHome.resolve(ApplicationTypeConstants.BLOCKLY_WORKSPACE_XML);
				if(Files.exists(blocklyWorkspace)){
					app.setContents(new String(Files.readAllBytes(blocklyWorkspace)));
				}

				Path applicationPy = applicationHome.resolve(ApplicationTypeConstants.PYTHON_APPLICATION_PY);
				if(Files.exists(applicationPy)){
					app.setGeneratedContents(new String(Files.readAllBytes(applicationPy)));
				}
				   break;
			   default:
			}
			
			applicationRepository.save(app);
		}catch(InvalidPathException e){
			log.info("Path "+applicationHome.toString()+" does not contain a "+APPLICATION_YML+" definition.");
		} catch (IOException e) {
			log.info("File "+applicationHome.toString()+File.separator+APPLICATION_YML+" cannot be read.");
		}
		
	}

}