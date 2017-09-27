function Chiby() {
  this.statusTimer = null;
  this.statusUpdateCallbacks = [];
}


Chiby.prototype.start = function(){
    console.log( "Started");
    this.invokeGetStatusTimeout();
};

Chiby.prototype.addStatusUpdateCallback = function(listener){
    this.statusUpdateCallbacks.push(listener);
};

Chiby.prototype.invokeGetStatusTimeout = function(){
    chiby.updateStatus();
    
    chiby.statusTimer = setTimeout(function(){chiby.invokeGetStatusTimeout()}, 3000);
};

Chiby.prototype.updateStatus= function() {
  $.getJSON( "/status", function(reply) {
    console.log( "Chiby Status > Running? "+reply.running+" Exit Code : " +reply.exitCode );
    
    $.each(chiby.statusUpdateCallbacks, function( cb){
            try{
                this(reply);
            }catch(err){
                console.log("Could not update service status : "+err);
            }
    }, reply);

  })
  .fail(function() {
    console.log( "error : Could not obtain chiby  status." );
    $.each(chiby.statusUpdateCallbacks, function( cb ){
            try{
                this({running: -3, exitCode: -1});
            }catch(err){
                console.log("Could not update service status : "+err);
            }
    });
   

  });
};


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
