<template>
  <b-container>
    <golden-layout id="gl-top" borderWidth="12" :showMaximiseIcon="false" :showPopoutIcon="false">
      <gl-row>
        <gl-row>
             <gl-component width="80" ref="blocklyContainer" title="Blockly" @resize="goldenLayoutResizeHandler" :showMaximiseIcon="false" :closable="false" :reorder-enabled="false">
                  <div id="blocklyArea"  ref="editorArea">  
                    <!-- v-bind:style="styleObject" -->
                  </div>
              </gl-component>
              <gl-stack :closable="false">
              <gl-component width="20" :closable="false" title="Code">
                <p id="code">
                  <button v-on:click="showCode()">Show JavaScript</button>
                  <pre v-html="code"></pre>
                </p>
              </gl-component>
              <gl-component width="20"  :closable="false" title="Console">
                <h1>Component 3</h1>
              </gl-component>
            </gl-stack>
        </gl-row>
      </gl-row>
    </golden-layout>  
    <div ref="editor" style="position:absolute"></div>
  </b-container>
</template>



<script>
import Vue from 'vue'
import Blockly from 'blockly';
//import VueLodash from 'vue-lodash'
//import lodash from 'lodash'
//Vue.use(VueLodash, { lodash: { lodash } })

import vgl from 'vue-golden-layout'
Vue.use(vgl);
import 'golden-layout/src/css/goldenlayout-light-theme.css'

export default {
  name: 'ChibyBlockly',
  components: {
  },
  data(){
    return {
      code: '',
      workspace: null,
      blocklyInstance: null,
      options: {
        media: 'media/',
        grid:
          {
            spacing: 25,
            length: 3,
            colour: '#ccc',
            snap: true
          },
        zoom:
            {controls: true,
            wheel: true,
            startScale: 1.0,
            maxScale: 3,
            minScale: 0.3,
            scaleSpeed: 1.2},
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
  computed: {
     styleObject: function() {
         return{
          height: this.dynHeight,
          width: this.dynWidth,
          "text-align": 'left'
         }
      }
  },
  methods: {
      goldenLayoutResizeHandler(e) {
         if(e) console.log(e,  this.workspace, this.blocklyInstance);
         var blocklyArea = this.$refs['editorArea'];
         blocklyArea.height = this.$el.offsetHeight;


         var blocklyDiv = this.$refs['editor'];
         var element = blocklyArea;
         var x = 0;
         var y = 0;
         do {
           x += element.offsetLeft;
           y += element.offsetTop;
           element = element.offsetParent;
         } while (element);
         blocklyDiv.style.left = x + 'px';
         blocklyDiv.style.top = y + 'px';
         blocklyDiv.style.width = (blocklyArea.offsetWidth+2) + 'px';
         blocklyDiv.style.height = ((blocklyArea.offsetHeight?blocklyArea.offsetHeight:blocklyArea.height)-20) + 'px';
         this.blocklyInstance.svgResize(this.workspace);
     },
  },
  mounted() {
    console.log('mounting Chiby Blockly editor')
    this.workspace = Blockly.inject(this.$refs['editor'],this.options);
    this.blocklyInstance = Blockly;
    console.log('mounted Chiby Blockly editor')
    
    this.goldenLayoutResizeHandler();

  }
}
</script>

<style scoped>
#blocklyArea {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

#gl-top {
 height: 85vh;
}      
      
</style>