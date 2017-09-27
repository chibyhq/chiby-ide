package com.github.chiby.ide.frontend;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.chiby.player.model.Application;
import com.github.chiby.player.model.ApplicationTypeConstants;
import com.github.chiby.store.model.repositories.ApplicationRepository;
import com.google.common.collect.ImmutableList;

import lombok.extern.java.Log;

@Component
@Log
public class ChibyHomeApplicationSerializer {

	@Autowired
	ApplicationRepository repository;

	@Autowired
	FrontendConfig config;

	FileSystem fileSystem = FileSystems.getDefault();

	public void persistAllApplications() {
		for (Application app : repository.findAll()) {
			persistApplication(app);
		}
	}

	public boolean persistApplication(Application app) {
		Path appHome = fileSystem.getPath(config.getHome()).resolve(app.getUuid().toString());
		if (!Files.exists(appHome)) {
			try {
				Files.createDirectory(appHome);
			} catch (IOException e) {
				log.log(Level.SEVERE, "Could not create application home directory for " + app.getTitle(), e);
			}
		}
		if (Files.isDirectory(appHome) && Files.isWritable(appHome)) {
			try {
				ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
				mapper.writeValue(
						Files.newOutputStream(appHome.resolve(ApplicationTypeConstants.APPLICATION_YAML_FILE)), app);

				switch (app.getType()) {
				case PYTHON:
				case PYGAMEZERO:
					if (app.getContents() != null && (!app.getContents().isEmpty())) {
						Files.write(appHome.resolve(ApplicationTypeConstants.BLOCKLY_WORKSPACE_XML),
								ImmutableList.of(app.getContents()), StandardCharsets.UTF_8);
					}
					if (app.getGeneratedContents() != null) {
						Files.write(appHome.resolve(ApplicationTypeConstants.PYTHON_APPLICATION_PY),
								ImmutableList.of(app.getGeneratedContents()), StandardCharsets.UTF_8);
					}
					break;
				case DOCKER:
				}
			} catch (IOException ioe) {
				log.log(Level.WARNING, "Could not save application contents to file system", ioe);
				return false;
			}

		}
		return true;
	}
}
