import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: '',
        userId: '',
    },
    mutations: {
        SAVE_TOKEN(state, value) {
            state.token = value
            localStorage.setItem('Token', value);
        },
        SAVE_USER_ID(state, value) {
            state.userId = value
            localStorage.setItem('userId', value)
        }
    },
    actions: {},
    modules: {}
})
