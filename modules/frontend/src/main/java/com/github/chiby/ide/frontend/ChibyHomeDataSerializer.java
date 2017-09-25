package com.github.chiby.ide.frontend;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.chiby.player.model.Application;
import com.github.chiby.store.model.repositories.ApplicationRepository;

@Component
public class ChibyHomeDataSerializer {

	@Autowired
	ApplicationRepository repository;
	
	FileSystem fileSystem = FileSystems.getDefault();
	
	public void persistApplications(){
		Iterable<Application> apps = repository.findAll();
		// Write all application definitions to the filesystem
	}
}
