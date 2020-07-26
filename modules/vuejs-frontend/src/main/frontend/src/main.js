import Vue from 'vue'
import App from './App.vue'
import store from './store'

Vue.config.ignoredElements = ['field','block','category','xml','mutation','value','sep']

Vue.config.productionTip = false

new Vue({
  store,
  render: h => h(App)
}).$mount('#app')
