import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue'),
    meta:{
      title:"登录"
    }
  },
  {
    path: '/',
    name: 'main',
    component: () => import('../views/Home'),
    redirect:'/index',
    children:[
      {
        path:'/index',
        name:'index',
        component: () => import('../views/Index')
      },
      {
        path:'/emp',
        name:'emp',
        component: () => import('../views/Emp')
      },
      {
        path:'/dept',
        name:'dept',
        component: () => import('../views/Dept')
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
