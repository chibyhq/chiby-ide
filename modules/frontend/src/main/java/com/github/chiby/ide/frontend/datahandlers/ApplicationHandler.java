package com.github.chiby.ide.frontend.datahandlers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.github.chiby.ide.frontend.ChibyHomeApplicationSerializer;
import com.github.chiby.player.model.Application;

@Component
@RepositoryEventHandler(Application.class)
public class ApplicationHandler {
	
	@Autowired
	ChibyHomeApplicationSerializer serializer;
	
	@HandleBeforeSave
	public void beforeSave(Application app){
		app.setCreatedOn(new Date());
	}
	
	@HandleAfterSave
	public void persistApplicationToFileSystem(Application app) {
		serializer.persistApplication(app);
	}

}
