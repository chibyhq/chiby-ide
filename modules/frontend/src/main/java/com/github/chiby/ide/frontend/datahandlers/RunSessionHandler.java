package com.github.chiby.ide.frontend.datahandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.github.chiby.player.model.RunSession;

/**
 * 
 * @author bcopy
 *
 */

@RepositoryEventHandler(RunSession.class)
@Component
public class RunSessionHandler {
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
		
	}
	
	@HandleBeforeSave
	public void interruptRunSession(RunSession runSession) {
		if((!runSession.getRunning()) && (!runSession.getStopped())){
//			executor.stop()
		}
		
	}
}
