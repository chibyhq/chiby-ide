function Chiby() {
  this.statusTimer = null;
  this.statusUpdateCallbacks = [];
  this.applications = [];
  this.dataApi = hybind("/data");
  this.dataApi.$bind("applications", this.applications);
  this.currentApp = null;
}


Chiby.prototype.showHome = function(){
     $("#panel-program").collapse("hide");
     $("#panel-home").collapse("show");
}

Chiby.prototype.refreshHome = function(){
    var that = this;
    this.applications.$load().then(function(){
        // Empty the grid and populate it again
        $("#applications-grid").empty();
        $.each(that.applications, function(index,app){
           var appItem = $('<div class="grid-item"></div>');
           var innerPanel = $('<div class="panel panel-primary" style="height:280px"></div>');
           var innerPanelHeader = $('<div class="panel-heading"></div>');
           innerPanelHeader.text(app.title);
           innerPanel.append(innerPanelHeader);
           var avatar = 'question-sign';
           if(app.avatar != null) avatar = app.avatar;
           var span = $('<div style="height:180px; padding:5px"><span style="font-size:8em; display:block; text-align:center" class="glyphicon glyphicon-'+avatar+'"></span><div style="padding:5px">'+app.description+'</div></div>');
           innerPanel.append(span);
           var innerPanelFooter = $('<div class="panel-footer text-right" style="height:60px"></div>');
           var editButton = $('<button type="button" class="btn btn-primary pull-right" onclick="chiby.openApplication($(this).data(\'app\'))">Edit</button>');
           editButton.data('app', app);
           innerPanelFooter.append(editButton);
           innerPanel.append(innerPanelFooter);
           appItem.append(innerPanel);
           $("#applications-grid").packery().append(appItem).packery('appended', appItem);
        });
    });
}

Chiby.prototype.openApplication = function(app){
     $("#panel-home").collapse("hide");
     $("#panel-program").collapse("show");
     this.currentApp = app;
     blocklyWorkspace.clear();
     if(app.contents != null){
       dom = Blockly.Xml.textToDom(app.contents);
       Blockly.Xml.domToWorkspace(dom, blocklyWorkspace);
     }
}

Chiby.prototype.createApplication = function(newApp){
     $("#panel-home").collapse("hide");
     $("#panel-program").collapse("show");
     this.currentApp = app;
     blocklyWorkspace.clear();
     if(app.contents != null){
       dom = Blockly.Xml.textToDom(app.contents);
       Blockly.Xml.domToWorkspace(dom, blocklyWorkspace);
     }
}

Chiby.prototype.runApplication = function(){
   if(this.currentApp != null){
       this.currentApp.$save();
       // Now create an association to a new runSession
   }
}


Chiby.prototype.executeScript= function(scriptContents) {
  $.ajaxSetup({ 
    contentType: "application/json"
  });
  $.post( "/execute","{\"scriptText\":\"import time\\nprint 'hello world'\\ntime.sleep(10)\"}", function(reply) {
    if(reply.success){
      // Update the status immediately
      chiby.updateStatus();
    }else{
      window.alert("Script execution failed : "+reply.message);
    }
  },"json");
};

chiby = new Chiby();

/*
 * Blockly.Python.workspaceToCode(blocklyWorkspace)
 * Blockly.Xml.workspaceToDom(blocklyWorkspace)
 */