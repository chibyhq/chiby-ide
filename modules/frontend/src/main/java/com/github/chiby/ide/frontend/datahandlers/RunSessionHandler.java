package com.github.chiby.ide.frontend.datahandlers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.github.chiby.ide.frontend.util.ApplicationHomeResolver;
import com.github.chibyhq.playar.PygamezeroExecutor;
import com.github.chibyhq.playar.model.Application;
import com.github.chibyhq.playar.model.RunSession;
import com.github.chibyhq.store.model.repositories.ApplicationRepository;
import com.github.chibyhq.store.model.repositories.RunSessionRepository;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;

import lombok.extern.java.Log;

@Log
@RepositoryEventHandler(RunSession.class)
@Component
public class RunSessionHandler {
	@Autowired
	PygamezeroExecutor pgzExecutor;

	@Autowired
	RunSessionRepository runSessionRepository;
	
	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	ApplicationHomeResolver applicationHomeResolver;

	/**
	 * When a new run session is created, we immediately connect it to a Docker
	 * run. We will post docker logs outputs as log entries connected this this
	 * run session asynchronously.
	 * 
	 * @param runSession
	 *            The newly created run session.
	 * @throws InterruptedException
	 * @throws DockerException
	 * @throws DockerCertificateException
	 */
	@HandleAfterCreate
	public void connectRunSessionToExecution(RunSession runSession) {
		Application app= applicationRepository.findOne(runSession.getApplicationUUID());
		switch (app.getType()) {
		case PYGAMEZERO:
			try {
				pgzExecutor.start(app, runSession,
						applicationHomeResolver.getPathForApplication(app));
			} catch (Exception e) {
				runSession.setStoppedAt(new Date());
				runSession.setStopped(true);
				runSession.setRunning(false);
				runSession.setStoppedAt(new Date());
				runSession.setExitMessage(e.getMessage());
				runSession.setExitCode(-1);
				runSessionRepository.save(runSession);

			}
			break;
		default:
			throw new UnsupportedOperationException(
					"Application type " + app.getType() + " is not yet supported");
		}

	}

	@HandleAfterSave
	public void interruptRunSession(RunSession runSession) {
//		if ((runSession.getRunning()) && (runSession.getStopped())) {
//			switch (runSession.getApplication().getType()) {
//			case PYGAMEZERO:
//				try {
//					// The run session was asked to stop running
//					pgzExecutor.stop(runSession, true);
//				} catch (Exception e) {
//					log.log(Level.SEVERE, "Could not stop session " + runSession.uuid, e);
//				}
//				break;
//			default:
//				throw new UnsupportedOperationException(
//						"Application type " + runSession.getApplication().getType() + " is not yet supported");
//			}
//		}

	}
}
