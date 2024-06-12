import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import TeacherLogin from '../views/TeacherLogin.vue';
import Classroom from '../views/Classroom.vue';

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/teacher', name: 'TeacherLogin', component: TeacherLogin },
  { path: '/classroom/:classCode', name: 'Classroom', component: Classroom, props: true }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
