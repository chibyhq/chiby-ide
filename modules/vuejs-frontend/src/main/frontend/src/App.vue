<template>
  <div id="app">
  <h1>Title</h1>
      <golden-layout id="gl-top"  :showMaximiseIcon="false" :showPopoutIcon="false">
      <gl-row>
        <gl-row>
             <gl-component title="Blockly" :showMaximiseIcon="false" :closable="false" :reorder-enabled="false">
              <BlocklyComponent id="blockly2" :options="options" ref="foo"></BlocklyComponent>
            </gl-component>
             <gl-stack :closable="false">
              <gl-component  :closable="false" title="component2">
                <p id="code">
                  <button v-on:click="showCode()">Show JavaScript</button>
                  <pre v-html="code"></pre>
                </p>
              </gl-component>
              <gl-component   :closable="false" title="component3">
                <h1>Component 3</h1>
              </gl-component>
            </gl-stack>
        </gl-row>
      </gl-row>
    </golden-layout>  
    
  </div>
</template>



<script>
import Vue from 'vue'

import BlocklyJS from 'blockly/javascript';

//import { SplitGrid, SplitGridArea, SplitGridGutter } from 'vue-split-grid';
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import vgl from 'vue-golden-layout'

Vue.use(vgl);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import 'golden-layout/src/css/goldenlayout-light-theme.css'

//import './custom.scss'

import BlocklyComponent from './components/BlocklyComponent.vue'

export default {
  name: 'app',
  components: {
    BlocklyComponent,
 //   SplitGrid,
 //   SplitGridArea,
 //   SplitGridGutter
  },
  data(){
    return {
      code: '',
      options: {
        media: 'media/',
        grid:
          {
            spacing: 25,
            length: 3,
            colour: '#ccc',
            snap: true
          },
        toolbox:
        `<xml>
          <category name="Logic" colour="%{BKY_LOGIC_HUE}">
            <block type="controls_if"></block>
            <block type="logic_compare"></block>
            <block type="logic_operation"></block>
            <block type="logic_negate"></block>
            <block type="logic_boolean"></block>
          </category>
          <category name="Loops" colour="%{BKY_LOOPS_HUE}">
            <block type="controls_repeat_ext">
              <value name="TIMES">
                <block type="math_number">
                  <field name="NUM">10</field>
                </block>
              </value>
            </block>
            <block type="controls_whileUntil"></block>
          </category>
          <category name="Math" colour="%{BKY_MATH_HUE}">
            <block type="math_number">
              <field name="NUM">123</field>
            </block>
            <block type="math_arithmetic"></block>
            <block type="math_single"></block>
          </category>
          <category name="Text" colour="%{BKY_TEXTS_HUE}">
            <block type="text"></block>
            <block type="text_length"></block>
            <block type="text_print"></block>
          </category>
          <category name="Variables" custom="VARIABLE" colour="%{BKY_VARIABLES_HUE}">
            </category>
        </xml>`
      }
    }
  },
  methods: {
    showCode() {
      this.code = BlocklyJS.workspaceToCode(this.$refs["foo"].workspace);
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  overflow: hidden;
  height: 95vh;
  width: 1280px;
}
html, body {
  margin: 0;
}
#code {
  right: 0;
  bottom: 0;
  width: 100%;
  height: 90vh;
  margin: 0;
}
#blockly2 {
  left: 0;
  bottom: 0;
  width: 500px;
  height: 500px;
}

#gl-top {
 height: 100%;
 width: 100%;
}

</style>