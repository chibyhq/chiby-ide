package com.github.chiby.ide.frontend.util;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.chiby.ide.frontend.FrontendConfigProperties;
import com.github.chiby.player.model.Application;

import lombok.Data;

@Component
@Data
public class ApplicationHomeResolver {
	FileSystem fileSystem = FileSystems.getDefault();
	
	@Autowired
	FrontendConfigProperties config;
	
	public Path getPathForApplication(Application app){
		return fileSystem.getPath(config.getHome()).resolve(app.getUuid().toString());
	}
}
