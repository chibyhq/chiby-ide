package com.github.chiby.ide.frontend.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;

import com.github.chiby.ide.frontend.FrontendApplication;

import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;

public class IdeTrayIcon {

	private static final String TOOLTIP = "Running";

	private SystemTray systemTray;

	public IdeTrayIcon() {
		systemTray = SystemTray.get();
		if (systemTray == null) {
			throw new RuntimeException("Unable to load SystemTray!");
		}
		try {
			systemTray.setImage(IdeTrayIcon.class.getResource("/static/ide/favicon.png").openStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		systemTray.setStatus(TOOLTIP);
		systemTray.getMenu().add(new MenuItem("Quit", new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				SpringApplication.exit(FrontendApplication.getContext(), new ExitCodeGenerator() {
					final int exitCode = 0;
					@Override
					public int getExitCode() {
						return exitCode;
					}
				});
				systemTray.shutdown();
			}
		})).setShortcut('q');
	}

}