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

import com.github.chiby.player.model.Application;
import com.github.chiby.store.model.repositories.ApplicationRepository;

import lombok.extern.java.Log;

/**
 * This initializer class inspects all project folders on the file system
 * and populates the repositories.
 * @author bcopy
 *
 */
@Log
public class ChibyHomeDataInitializer implements ApplicationRunner{

	private static final String APPLICATION_YML = ".application.yml";

	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	FrontendConfig frontendConfig;
	
	FileSystem fileSystem = FileSystems.getDefault();
	
	Yaml yaml = new Yaml();

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		// Look into each project folder for an application.yml file
		// to deserialize
		Path homePath = fileSystem.getPath(frontendConfig.getHome());
		if(Files.isDirectory(homePath)){
			Files.newDirectoryStream(fileSystem.getPath(frontendConfig.getHome() ) ).forEach(this::findApplicationDefinition);
		}
	}
	
	private void findApplicationDefinition(Path projectFolder){
		try{
			Path applicationYamlPath = projectFolder.resolve(APPLICATION_YML);
			Application app = yaml.loadAs(Files.newInputStream(applicationYamlPath), Application.class);
			applicationRepository.save(app);
		}catch(InvalidPathException e){
			log.info("Path "+projectFolder.toString()+" does not contain a "+APPLICATION_YML+" definition.");
		} catch (IOException e) {
			log.info("File "+projectFolder.toString()+File.separator+APPLICATION_YML+" cannot be read.");
		}
		
	}
	
	
}
