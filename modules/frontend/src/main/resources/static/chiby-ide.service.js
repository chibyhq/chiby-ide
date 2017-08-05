function Whatnot() {
  this.statusTimer = null;
  this.statusUpdateCallbacks = [];
}


Whatnot.prototype.start = function(){
    console.log( "Started");
    this.invokeGetStatusTimeout();
};

Whatnot.prototype.addStatusUpdateCallback = function(listener){
    this.statusUpdateCallbacks.push(listener);
};

Whatnot.prototype.invokeGetStatusTimeout = function(){
    whatnot.updateStatus();
    
    whatnot.statusTimer = setTimeout(function(){whatnot.invokeGetStatusTimeout()}, 3000);
};

Whatnot.prototype.updateStatus= function() {
  $.getJSON( "/status", function(reply) {
    console.log( "Whatnot Status > Running? "+reply.running+" Exit Code : " +reply.exitCode );
    
    $.each(whatnot.statusUpdateCallbacks, function( cb){
            try{
                this(reply);
            }catch(err){
                console.log("Could not update service status : "+err);
            }
    }, reply);

  })
  .fail(function() {
    console.log( "error : Could not obtain whatnot  status." );
    $.each(whatnot.statusUpdateCallbacks, function( cb ){
            try{
                this({running: -3, exitCode: -1});
            }catch(err){
                console.log("Could not update service status : "+err);
            }
    });
   

  });
};


Whatnot.prototype.executeScript= function(scriptContents) {
  $.ajaxSetup({ 
    contentType: "application/json"
  });
  $.post( "/execute","{\"scriptText\":\"import time\\nprint 'hello world'\\ntime.sleep(10)\"}", function(reply) {
    if(reply.success){
      // Update the status immediately
      whatnot.updateStatus();
    }else{
      window.alert("Script execution failed : "+reply.message);
    }
  },"json");
//  .fail(function() {
//    console.log( "error : Could not execute whatnot script." );
//  });
};

whatnot = new Whatnot();
