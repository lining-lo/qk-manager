import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/Layout.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/login/index.vue')
    },
    {
      path: '/',
      component: Layout,
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'Home',
          component: () => import('@/views/home/index.vue')
        },
        // 线索管理
        {
          path: '/clue/list',
          name: 'clueList',
          component: () => import('@/views/clue/list.vue')
        },
        {
          path: '/clue/pool',
          name: 'cluePool',
          component: () => import('@/views/clue/pool.vue')
        },
        // 商机管理
        {
          path: '/business/list',
          name: 'businessList',
          component: () => import('@/views/business/list.vue')
        },
        {
          path: '/business/pool',
          name: 'businessPool',
          component: () => import('@/views/business/pool.vue')
        },
        // 客户管理
        {
          path: '/customer',
          name: 'customer',
          component: () => import('@/views/customer/index.vue')
        },
        // 资源管理
        {
          path: '/resource/course',
          name: 'course',
          component: () => import('@/views/resource/course.vue')
        },
        {
          path: '/resource/activity',
          name: 'activity',
          component: () => import('@/views/resource/activity.vue')
        },
        // 系统管理
        {
          path: '/system/department',
          name: 'Department',
          component: () => import('@/views/system/department.vue')
        },
        {
          path: '/system/role',
          name: 'Role',
          component: () => import('@/views/system/role.vue')
        },
        {
          path: '/system/user',
          name: 'User',
          component: () => import('@/views/system/user.vue')
        },
        {
          path: '/system/log',
          name: 'SystemLog',
          component: () => import('@/views/system/log.vue')
        }
      ]
    }
  ]
})

// 路由守卫
// router.beforeEach((to, from, next) => {
//   const token = localStorage.getItem('token')
//   if (to.path === '/login') {
//     if (token) {
//       next('/')
//     } else {
//       next()
//     }
//   } else {
//     if (token) {
//       next()
//     } else {
//       next('/login')
//     }
//   }
// })

export default router