package com.github.chiby.ide.frontend.util;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.chiby.ide.frontend.FrontendConfigProperties;
import com.github.chibyhq.playar.model.Application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationHomeResolver {
	FileSystem fileSystem = FileSystems.getDefault();
	
	@Autowired
	FrontendConfigProperties config;
	
	public Path getPathForApplication(Application app){
		return fileSystem.getPath(config.getHome()).resolve(app.getUuid().toString());
	}
}
