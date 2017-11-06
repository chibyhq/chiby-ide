function Chiby() {

	this.statusTimer = null;
	this.statusUpdateCallbacks = [];
	this.applications = [];
	this.runSessions = [];
	this.logEntries = [];
	this.dataApi = hybind("/data");
	var that = this;
	this.applicationsCache = {};
	this.dataApi.$bind("applications", this.applications).$load().then(
			function() {
				that.applications.forEach(function(application) {
					application.$share(that.applicationsCache);
				});
			});
	this.dataApi.$bind("runSessions", this.runSessions);
	this.currentApp = null;
	this.currentRunSession = null;
}

Chiby.prototype.showHome = function() {
	$("#panel-program").collapse("hide");
	$("#panel-home").collapse("show");
}

Chiby.prototype.refreshHome = function() {
	var that = this;
	this.applications
			.$load()
			.then(
					function() {
						// Empty the grid and populate it again
						$("#applications-grid").empty();
						$
								.each(
										that.applications,
										function(index, app) {
											var appItem = $('<div id="'
													+ app.uuid
													+ '" class="grid-item"></div>');
											var innerPanel = $('<div class="panel panel-primary" style="height:280px"></div>');
											var innerPanelHeader = $('<div class="panel-heading"></div>');
											innerPanelHeader.text(app.title);
											innerPanel.append(innerPanelHeader);
											var avatar = 'question-sign';
											if (app.avatar != null)
												avatar = app.avatar;
											var span = $('<div style="height:180px; padding:5px"><span style="font-size:8em; margin-top: 10px; color: #111111; display:block; text-align:center" class="glyphicon glyphicon-'
													+ avatar
													+ '"></span><div style="padding:5px">'
													+ app.description
													+ '</div></div>');
											innerPanel.append(span);
											var innerPanelFooter = $('<div class="panel-footer text-right" style="height:60px"></div>');
											var editButton = $('<button type="button" class="btn btn-primary pull-right" style="margin:3px" onclick="chiby.openApplication($(this).data(\'app\'))">Open</button>');
											editButton.data('app', app);
											var deleteButton = $('<button type="button" class="btn btn-danger pull-left" style="margin:3px" onclick="chiby.deleteApplication($(this).data(\'app\'))">Delete</button>');
											deleteButton.data('app', app);
											innerPanelFooter.append(editButton);
											innerPanelFooter
													.append(deleteButton);
											innerPanel.append(innerPanelFooter);
											appItem.append(innerPanel);
											$("#applications-grid").packery()
													.append(appItem)
													.packery('appended',
															appItem);
										});
					});
}

Chiby.prototype.openApplication = function(app) {
	$("#panel-home").collapse("hide");
	$("#panel-program").collapse("show");
	this.currentApp = app;
	blocklyWorkspace.clear();
	if (app.contents != null) {
		dom = Blockly.Xml.textToDom(app.contents);
		Blockly.Xml.domToWorkspace(dom, blocklyWorkspace);
	}
}

Chiby.prototype.deleteApplication = function(app) {
	var uuid = app.uuid;
	app.$delete().then(function() {
		// this fails with a JQuery error in Sizzle
		// var elem = $('#'+uuid);
		// $("#applications-grid").packery('remove',elem);
		chiby.refreshHome();
	});
}

Chiby.prototype.createNewApplication = function() {
	this.applications.$create({
		title : $('#new-app-title').val(),
		avatar : $('#new-app-avatar').val().toLowerCase().replace(/\s/g, "-"),
		description : $('#new-app-description').val(),
		type : 'PYGAMEZERO'
	}).then(function(newApplication) {
		chiby.refreshHome();
	});
}

Chiby.prototype.clearConsoleOutput = function() {
	$('#chiby-console-textarea').text("");
}

Chiby.prototype.runApplication = function() {
	if (chiby.currentApp != null) {
		chiby.currentApp.$save().then(
				function(savedApp) {
					// Now create an association to a new runSession
					chiby.runSessions.$create({
						'applicationUUID' : savedApp.uuid
					}).then(
							function(runSession) {
								// Keep track of the run session by its ID
								chiby.currentRunSession = runSession;
								// Fire the watcher thread to collect log
								// outputs
								// and update the RUN button
								console.log("created run session "
										+ chiby.currentRunSession.uuid);

								chiby.startRunSessionStatusTimer();
							});
				});
	}
}

Chiby.prototype.startRunSessionStatusTimer = function() {
	chiby.clearConsoleOutput();
	chiby.invokeRunSessionStatusTimer();
}

Chiby.prototype.invokeRunSessionStatusTimer = function() {
	chiby.updateStatus();
	if (chiby.currentRunSession != null && chiby.currentRunSession.running) {
		chiby.statusTimer = setTimeout(function() {
			chiby.invokeRunSessionStatusTimer()
		}, 1000);
	}
};

Chiby.prototype.updateStatus = function() {
	if (chiby.currentRunSession != null) {
		chiby.currentRunSession
				.$load()
				.then(
						function(runSession) {
							// Reflect the status of the run session into the UI
							$("#chiby-status-icon").attr("class", "glyphicon");
							$("#chiby-script-action-icon").attr("class",
									"glyphicon");

							if (runSession.running) {
								$("#chiby-status-label").text("Running");
								$("#chiby-script-action-label").text("Stop");
								$("#chiby-status-icon").addClass(
										"glyphicon-refresh");
								$("#chiby-status-icon").addClass(
										"glyphicon-spin");
								$("#chiby-script-action-icon").addClass(
										"glyphicon-stop");
								$("#chiby-script-action-button").off("click");
								$("#chiby-script-action-button").on("click",
										chiby.stopApplication);
							} else if (!runSession.running) {
								$("#chiby-status-label").text("Stopped");
								$("#chiby-script-action-label").text("Run");
								$("#chiby-status-icon").removeClass(
										"glyphicon-spin");
								$("#chiby-status-icon").addClass(
										"glyphicon-stop");
								$("#chiby-script-action-icon").addClass(
										"glyphicon-play");
								$("#chiby-script-action-button").off("click");
								$("#chiby-script-action-button").on("click",
										chiby.runApplication);
							}
						})
				.fail(
						function() {
							chiby.currentRunSession = null;
							$("#chiby-status-icon").attr("class", "glyphicon");
							$("#chiby-script-action-icon").attr("class",
									"glyphicon");
							// Status is unknown, it could not be queried from
							// the chiby service
							$("#chiby-status-label").text("Unknown");
							$("#chiby-status-icon").removeClass(
									"glyphicon-spin");
							$("#chiby-script-action-icon").addClass(
									"glyphicon-play");
							$("#chiby-status-icon").addClass(
									"glyphicon-question-sign");
							$("#chiby-script-action-button").off("click");
							$("#chiby-script-action-button")
									.on(
											"click",
											function() {
												window
														.alert("Cannot execute code : Chiby server unreachable !");
											});

						});

		// If stopped, collect the standard outputs and display them
		if (!chiby.currentRunSession.running) {
			// append all log entries to the console output
			$.each(chiby.currentRunSession.logEntries, function(lineNumber,
					value) {
				try {
					$("#chiby-console-textarea").append(value.line + '\n');
				} catch (err) {
					console.log("Could not update console output : " + err);
				}
			});
			// If stopped, collect the exitCode and display a toast notification
			// if things went wrong.
			if (chiby.currentRunSession.exitCode != 0) {
				// TODO : display a toast that hides after a few seconds
				window.alert("Program exited with code "
						+ chiby.currentRunSession.exitCode);
				$("#navtab-console").tab('show');
			}
		}

	}
}

chiby = new Chiby();
