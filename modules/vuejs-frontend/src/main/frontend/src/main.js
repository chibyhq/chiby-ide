import Vue from 'vue'
import App from './App.vue'
import router from './router.js'
import store from './store'

// Ignore blockly elements in Vue
Vue.config.ignoredElements = ['field','block','category','xml','mutation','value','sep']

Vue.config.productionTip = false

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
