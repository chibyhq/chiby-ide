package com.github.chiby.ide.frontend;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.chiby.ide.frontend.util.ApplicationHomeResolver;
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
@Component
public class ChibyHomeDataInitializer implements ApplicationRunner {

	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	FrontendConfigProperties frontendConfig;
	
	@Autowired
	ChibyHomeApplicationSerializer serializer;
	
	@Autowired
	ApplicationHomeResolver applicationHomeResolver;

	FileSystem fileSystem = FileSystems.getDefault();

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Look into each project folder for an application.yml file
		// to deserialize
		Path homePath = fileSystem.getPath(frontendConfig.getHome());
		if( ( (!Files.exists(homePath)) || Files.list(homePath).count() == 0) && frontendConfig.isInitializeHome()){
			Files.createDirectories(homePath);
			Files.newDirectoryStream(Paths.get(ClassLoader.getSystemResource("sample-projects").toURI()))
			.forEach(this::findApplicationDefinition);
			
			if(serializer != null){
				serializer.persistAllApplications();
			}
		}
		else{
			if (Files.isDirectory(homePath) && Files.isReadable(homePath) && Files.isWritable(homePath)) {
				Files.newDirectoryStream(fileSystem.getPath(frontendConfig.getHome()))
						.forEach(this::findApplicationDefinition);
			}else{
				log.severe("Could not open the IDE home location '"+homePath+"' (is it writable ?)");
			}
		}
	}

	private void findApplicationDefinition(Path applicationHome){
		try{
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			Path applicationYamlPath = applicationHome.resolve(ApplicationTypeConstants.APPLICATION_YAML_FILE);
			
			Application app = mapper.readValue(Files.newInputStream(applicationYamlPath), Application.class);
			
			if(app.isTemplate()){
				// Before loading the template, give it a unique UUID
				app.setUuid(UUID.randomUUID());
				app.setTemplate(false);
				
				// Copy all resources in the folder to the destination location
				Path destination = applicationHomeResolver.getPathForApplication(app);
				String dir = "images";
				Files.createDirectories(destination.resolve(dir));
				copyFolder(applicationHome.resolve(dir), destination.resolve(dir));
			}
			
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
			log.info("Path "+applicationHome.toString()+" does not contain a "+ApplicationTypeConstants.APPLICATION_YAML_FILE+" definition.");
		} catch (IOException e) {
			log.log(Level.INFO, "I/O Exception occurred while reading application definition "+applicationHome.toString(), e);
		}
		
	}
	
	public  static void	copyFolder( Path src, Path dest )
	{
	    try
	    {   Files.walk( src ).forEach( s ->
		        {   try	            {   Path d = dest.resolve( src.relativize(s).toString() );
		                if( Files.isDirectory( s ) )	                {   if( !Files.exists( d ) )
		                        Files.createDirectory( d );
		                    return;
		                }
		                Files.copy( s, d );// use flag to override existing
		            }catch( Exception e )
		                { log.log(Level.INFO, "Exception occurred while copying application template file "+s.toString(), e); }
		        });
	    }catch( Exception ex )
	        {   ex.printStackTrace(); }
	}

}
