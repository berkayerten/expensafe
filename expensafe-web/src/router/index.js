import { createRouter, createWebHistory } from 'vue-router'
import AuthPage from '../views/AuthPage.vue'
import ExpenseOverview from '../views/ExpenseOverview.vue'
import UserDashboard from '../views/UserDashboard.vue'

const routes = [
  { path: '/', component: AuthPage },
  { path: '/overview', component: UserDashboard },
  { path: '/expense/:id', component: ExpenseOverview }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
