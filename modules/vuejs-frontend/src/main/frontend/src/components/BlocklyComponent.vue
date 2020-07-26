<template>
  <div>
    <div class="blocklyDiv" v-bind:style="styleObject" ref="blocklyDiv">
    </div>
    <xml ref="blocklyToolbox" style="display:none">
      <slot></slot>
    </xml>
  </div>
</template>

<script>
/**
 * @license
 * 
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @fileoverview Blockly Vue Component.
 * @author samelh@google.com (Sam El-Husseini)
 */

import Blockly from 'blockly';

export default {
  name: 'BlocklyComponent',
  props: ['options'],
  data(){
    return {
      workspace: null
    }
  },
  computed: {
      dynWidth: function(){ return '80vh'; }
     ,dynHeight: function(){ return '80vh'; }
     ,styleObject: function() {
         return{
          height: this.dynHeight,
          width: this.dynWidth,
          "text-align": 'left'
         }
      }
  },
  mounted() {
    var options = this.$props.options || {};
    if (!options.toolbox) {
      options.toolbox = this.$refs["blocklyToolbox"];
    }
    this.workspace = Blockly.inject(this.$refs["blocklyDiv"], options);
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.blocklySvg {
        height: 100%;
        width:100%;
      }
</style>