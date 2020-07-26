<template>
  <div id="chiby-blockly">
     <v-blockly id="blockly2" :options="options" ref="foo"></v-blockly>
           
     <p id="code">
                  <button v-on:click="showCode()">Show JavaScript</button>
                  <pre v-html="code"></pre>
                </p>
     <!--
     <golden-layout id="gl-top"  :showMaximiseIcon="false" :showPopoutIcon="false">
      <gl-row>
        <gl-row>
             <gl-component title="Blockly" @resize="resizeEventHandler" :showMaximiseIcon="false" :closable="false" :reorder-enabled="false">
              <v-blockly id="blockly2" :options="options" ref="foo"></v-blockly>
            </gl-component>
             <gl-stack  :closable="false">
              <gl-component  :closable="false" title="Code">
                <p id="code">
                  <button v-on:click="showCode()">Show JavaScript</button>
                  <pre v-html="code"></pre>
                </p>
              </gl-component>
              <gl-component   :closable="false" title="Console">
                <h1>Component 3</h1>
              </gl-component>
            </gl-stack>
        </gl-row>
      </gl-row>
    </golden-layout>  
    -->
  </div>
</template>



<script>
import Vue from 'vue'

import VBlockly, { Blockly } from 'v-blockly'
Vue.component('v-blockly', VBlockly)

import vgl from 'vue-golden-layout'

Vue.use(vgl);

import 'golden-layout/src/css/goldenlayout-light-theme.css'

//import BlocklyComponent from '@/components/BlocklyComponent.vue'

export default {
  name: 'ChibyBlockly',
  components: {
    'v-blockly' : VBlockly
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
      this.code = Blockly.workspaceToCode(this.$refs["foo"].workspace);
    },
    windowResizeEventHandler(e) {
         console.log(e);
       //  this.$refs["foo"]
     },
     resizeEventHandler() {
         console.log("Resized !",this.$el, this.$el.width, this.$el.height);
     },
     updateView(){
         console.log("switch to ",this.$store.state.currentApplication);
         var app = this.$store.state.currentApplication;
         if (app !=null){
           if(app.contents != null && app.contents != "") {
             var dom = window.Blockly.Xml.textToDom(app.contents);
             window.Blockly.Xml.domToWorkspace(dom, this.$refs["foo"].workspace);
           }
         }else{
             console.log("switch to ",this.$store.state.currentApplication);
         }
     }
     
  },
   updated(){
    console.log('updated')
    this.updateView()
  },
  mounted() {
    console.log('mounted')
    this.updateView()
  },
  created() {
   //   window.addEventListener("resize", this.windowResizeEventHandler);
    },
  destroyed() {
  //    window.removeEventListener("resize", this.windowResizeEventHandler);
    },

}
</script>

<style>
#chiby-blockly {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  overflow: hidden;
  height: 100vh;
  width: 1280px;
}
#blockly2 {
  height: 100vh;
  width: 1280px;

}
#code {
  right: 0;
  bottom: 0;
  margin: 5px;
}
#gl-top {
 height: 80vh;
 width: 1280px;
}

</style>