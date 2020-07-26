<template>
 <b-container ref="blocklyContainer">
  <div id="blocklyArea" v-bind:style="styleObject" ref="editor">
  </div>
   </b-container>
</template>



<script>
import Vue from 'vue'
import Blockly from 'blockly';
import VueLodash from 'vue-lodash'
import lodash from 'lodash'

Vue.use(VueLodash, { lodash: { lodash } })

export default {
  name: 'ChibyBlockly',
  components: {
  },
  data(){
    return {
      dynWidth:  '1200px',
      dynHeight:  '600px',
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
  },methods: {
      windowResizeEventHandler(e) {
         console.log(e,  this.workspace, this.blocklyInstance);
         this.dynWidth = this.$refs['blocklyContainer'].offsetWidth+'px';
         this.dynHeight = this.$refs['blocklyContainer'].offsetHeight+'px';
         this.blocklyInstance.svgResize(this.workspace);
     },
  },
  mounted() {
    console.log('mounted Chiby Blockly editor')
    this.workspace = Blockly.inject(this.$refs['editor'],this.options);
    this.blocklyInstance = Blockly;

  },
  created() {
      window.addEventListener("resize",  this.windowResizeEventHandler);
    },
  destroyed() {
      window.removeEventListener("resize", this.windowResizeEventHandler);
    }
}
</script>

<style>
#blocklyArea {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
.blocklySvg {
        height: 100%;
        width: 100%;
      }
</style>