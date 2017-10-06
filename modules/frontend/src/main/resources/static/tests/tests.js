QUnit.test("Hybind query", function(assert) {

	
  api = hybind("http://localhost:8080/data");    // API root
  applications = [];
  api.$bind("applications", applications);
  applications.$load().then(function() {  
     assert.ok(applications.length==0);
  }).then(function() {
      app = { title: "New App" };
      return applications.$create(app); 
  }).then(function() {  
     assert.ok(applications.length==1);
  });
  assert.ok(0==0);
});

