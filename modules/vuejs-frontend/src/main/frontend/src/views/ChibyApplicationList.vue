<template>
   <div ref="listRoot">
     <b-card-group columns> 
          <b-card no-body v-for="app in applications"  :key="app.uuid"
            tag="article"
            style="max-width: 20rem; min-width: 10rem;"
            class="mb-2"
          > <b-card-body>
               <h2 class="mb-0" style="text-align:center; white-space:nowrap">{{ app.title }}</h2>
               <b-container style="text-align:center; padding: 5px"> <b-icon class="rounded-circle bg-info p-2" font-scale="5" :icon="app.avatar" ></b-icon></b-container>
               <div style="padding: 5px">
               <b-container class="text-right" style="margin:5px">
                  <b-button class="float-left" @click="remove(app)" style="margin:3px" href="#" variant="danger">Delete</b-button>
                  <b-button class="float-right" @click="open(app)" style="margin:3px" href="#" variant="primary">Open</b-button>
               </b-container>
               </div>
            </b-card-body>
          </b-card>     
          
     </b-card-group>
     
     <b-button @click="add" variant="primary">Add</b-button>
  </div>  
</template>

<script>
import { v4 as uuidv4 } from 'uuid';

export default {
  name: 'ChibyApplicationList',
  computed:{
      applications() {
          return this.$store.state. applications;
      }
    
  },
  methods: {
     remove: function(app){
         //this.applications = this.applications.filter( entry => entry.uuid != app.uuid);
         //console.log("remove app ", app.uuid, app.title);
         this.$store.dispatch("removeApplication",app);
     },
     add: function(){
        this.$store.dispatch("addApplication",
              {
                uuid: uuidv4(),
                title: "Particle Ninja",
                avatar: "shield-slash",
                contents: ""
              }
            );
     },
     open: function(app) {
         this.$store.dispatch("setCurrentApplication",app)
            .then(() => {
               this.$router.push("/blockly");
            });   
     }
  }
}
</script>