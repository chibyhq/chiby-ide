import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
      applications: [{
            uuid: "2324-1234-3334-2234",
            title: "Flappy Proton",
            avatar: "lightning",
            contents: `<xml xmlns="http://www.w3.org/1999/xhtml" id="workspaceBlocks" style="display:none">
                          <block type="controls_repeat_ext" id="$+go@.cAFcS;I+kLKcJF" x="38" y="88">
                            <value name="TIMES">
                              <shadow type="math_number" id="J/i;Ee=:g;~i]*[ivr#j">
                                <field name="NUM">10</field>
                              </shadow>
                            </value>
                            <statement name="DO">
                              <block type="controls_if" id="j~4i=+n#-!:8@i:s4)sV">
                                <value name="IF0">
                                  <block type="logic_negate" id="ui50QhHRz)Sv;GN+AIt/">
                                    <value name="BOOL">
                                      <block type="logic_boolean" id="mG^m!mqsy;H=;5k-zxAZ">
                                        <field name="BOOL">FALSE</field>
                                      </block>
                                    </value>
                                  </block>
                                </value>
                                <statement name="DO0">
                                  <block type="controls_flow_statements" id="CN1F-,o)Qt;#;}LTmDCT">
                                    <field name="FLOW">BREAK</field>
                                  </block>
                                </statement>
                              </block>
                            </statement>
                          </block>
                        </xml>`
          },
          {
            uuid: "2324-1234-3334-2235",
            title: "Particle Ninja",
            avatar: "shield-slash",
            contents: `<xml xmlns="http://www.w3.org/1999/xhtml" id="workspaceBlocks" style="display:none">
                          <block type="controls_repeat_ext" id="$+go@.cAFcS;I+kLKcJF" x="38" y="88">
                            <value name="TIMES">
                              <shadow type="math_number" id="J/i;Ee=:g;~i]*[ivr#j">
                                <field name="NUM">10</field>
                              </shadow>
                            </value>
                            <statement name="DO">
                              <block type="controls_if" id="j~4i=+n#-!:8@i:s4)sV">
                                <value name="IF0">
                                  <block type="logic_negate" id="ui50QhHRz)Sv;GN+AIt/">
                                    <value name="BOOL">
                                      <block type="logic_boolean" id="mG^m!mqsy;H=;5k-zxAZ">
                                        <field name="BOOL">FALSE</field>
                                      </block>
                                    </value>
                                  </block>
                                </value>
                                <statement name="DO0">
                                  <block type="controls_flow_statements" id="CN1F-,o)Qt;#;}LTmDCT">
                                    <field name="FLOW">BREAK</field>
                                  </block>
                                </statement>
                              </block>
                            </statement>
                          </block>
                        </xml>`
          }],
      currentApplicationIndex: 0
  },
  getters: {
      currentApplication:  state => {
          return state.applications[state.currentApplicationIndex];
      }
  },
  mutations: {
      APPEND_APPLICATION(state, payload){
          state.applications.push(payload)
      },
      DELETE_APPLICATION(state, payload){
         // state.applications = state.applications.filter( entry => entry.uuid != payload.uuid)
          var index = state.applications.findIndex(entry => entry.uuid == payload.uuid);
          if(index != -1) {
              state.applications.splice(index, 1);
          }
      },
      SET_CURRENT_APPLICATION(state, payload){
          var index = state.applications.findIndex(entry => entry.uuid == payload.uuid);
           if(index != -1) {
               state.currentApplicationIndex = index
           }
      }
     
  },
  actions: {
      addApplication: ({ commit }, payload) => {
        commit('APPEND_APPLICATION', payload)
      },
      removeApplication: ({ commit }, payload) => {
        commit('DELETE_APPLICATION', payload)
      },
      setCurrentApplication: ({ commit }, payload) => {
        commit('SET_CURRENT_APPLICATION', payload)
      }
      
  },
  modules: {
  }
})
