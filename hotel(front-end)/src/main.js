import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router'
import store from './store'
import axios from 'axios'
import * as echarts from 'echarts';
import App from './App.vue'
import './assets/css/global.css'
import './assets/css/iconfont.css'


Vue.config.productionTip = false
//挂载到Vue原型上
Vue.prototype.$echarts = echarts
Vue.prototype.$axios = axios

Vue.use(ElementUI)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
