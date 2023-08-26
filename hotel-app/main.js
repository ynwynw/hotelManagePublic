import App from './App'
import uView from '@/uni_modules/uview-ui'
import {myRequest} from "util/api.js"
import {dateTimeFormat,dateFormat} from '@/util/format.js'
// import './static/global.css'
// import './static/iconfont.css'

// 挂载到全局
Vue.prototype.$myRequest = myRequest

Vue.use(uView)

Vue.filter('format',dateTimeFormat)

// 全局过滤器
Vue.filter("formatDate",dateFormat)


// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
    ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif