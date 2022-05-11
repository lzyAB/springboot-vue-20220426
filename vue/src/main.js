import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/css/global.css'

Vue.config.productionTip = false

import ElementUI from 'element-ui';

import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);

import axios from 'axios'

Vue.use(axios);
Vue.prototype.$axios = axios

router.beforeEach((to,from,next) => {
  store.commit('getToken')
  const token = store.state.user.token
  if(!token && to.name !== 'login'){
    next({ name : 'login' })
  }else {
    next()
  }
})

import request from "./util/request";
Vue.prototype.request = request  //全局注册request，其他文件可通过this.request使用分装好的axios实例




new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
