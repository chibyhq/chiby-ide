import Vue from 'vue'
import Router from 'vue-router'
import List from './views/ChibyApplicationList.vue'
import AppWizard from './views/ChibyApplicationWizard.vue'
import BlocklyEditor from './views/ChibyBlocklyBootstrap.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: List
    },
    {
      path: '/new',
      name: 'newApp',
      component: AppWizard
    },
    {
      path: '/blockly',
      name: 'blockly',
      component: BlocklyEditor
    }
  ]
})