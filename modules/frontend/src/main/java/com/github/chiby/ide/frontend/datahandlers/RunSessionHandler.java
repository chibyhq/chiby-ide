package com.github.chiby.ide.frontend.datahandlers;

import java.util.Date;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.github.chiby.player.DockerExecutor;
import com.github.chiby.player.model.RunSession;
import com.github.chiby.store.model.repositories.RunSessionRepository;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;

import lombok.extern.java.Log;

@Log
@RepositoryEventHandler(RunSession.class)
@Component
public class RunSessionHandler {
	@Autowired
	DockerExecutor executor;
	
	@Autowired
	RunSessionRepository runSessionRepository;
	
	/**
	 * When a new run session is created, we immediately connect it to a
	 * Docker run. We will post docker logs outputs as log entries connected
	 * this this run session asynchronously.
	 * @param runSession The newly created run session.
	 * @throws InterruptedException 
	 * @throws DockerException 
	 * @throws DockerCertificateException 
	 */
	@HandleAfterCreate
	public void connectRunSessionToExecution(RunSession runSession) {
		try{
		   executor.start(runSession.getApplication(), runSession);
		}catch(DockerException | DockerCertificateException | InterruptedException e){
			runSession.setStoppedAt(new Date());
			runSession.setStopped(true);
			runSession.setRunning(false);
			runSession.setStoppedAt(new Date());
			runSession.setExitMessage(e.getMessage());
			runSession.setExitCode(-1);
			runSessionRepository.save(runSession);
			
		}
	}
	
	@HandleAfterSave
	public void interruptRunSession(RunSession runSession) {
		if((runSession.getRunning()) && (runSession.getStopped())){
			try{
			  // The run session was asked to stop running
			  executor.stop(runSession, true);
			}catch(DockerException | DockerCertificateException | InterruptedException e){
			  log.log(Level.SEVERE, "Could not stop session "+runSession.uuid, e);
			}
		}
		
	}
}
