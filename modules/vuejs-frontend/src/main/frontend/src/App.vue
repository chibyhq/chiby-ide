<template>
  <div id="app">
  <h1>Title</h1>
   <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/new">New App</router-link> |
      <router-link to="/blockly">Blockly</router-link>
    </div>
    <b-container style="margin:10px 5px">
      <!--transition name="slide"-->
        <router-view></router-view>
      <!--/transition-->
    </b-container>
    
     <h3>footer</h3>
  </div>
</template>



<script>
import Vue from 'vue'
 

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'


export default {
  name: 'app',
  components: {
  },
  data(){
    return {
      brand: "Chiby"
    }
  },
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  overflow: hidden;
}
html, body {
  margin: 0;
}


</style>