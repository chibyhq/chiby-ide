package com.github.chiby.ide.frontend.datahandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.github.chiby.ide.frontend.ChibyHomeApplicationSerializer;
import com.github.chiby.player.model.Application;

@RepositoryEventHandler(Application.class)
@Component
public class ApplicationHandler {
	
	@Autowired
	ChibyHomeApplicationSerializer serializer;
	
	@HandleAfterSave
	public void persistApplication(Application app) {
		serializer.persistApplication(app);
	}

}
