import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from './views/Home.vue';
import Register from './views/Register.vue';
import Login from './views/Login.vue';
import Dashboard from './views/Dashboard.vue';
import TopTiemposView from './views/TopTiemposView.vue';
import TopAhorroAguaView from './views/TopAhorroAguaView.vue';
import Historial from './views/Historial.vue';
import NotFound from './views/NotFound.vue';

Vue.use(VueRouter);

export default new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        { 
            path: '/',
            name: 'Home',
            component: Home 
        },
        {
            path: '/register',
            name: 'Registro',
            component: Register
        },
        {
            path: '/login',
            name: 'Login',
            component: Login
        },
        {
            path: '/dashboard',
            name: 'Dashboard',
            component: Dashboard
        },
        {
            path: '/top-tiempos',
            name: 'Top Tiempos',
            component: TopTiemposView
        },
        {
            path: '/top-agua-ahorrada',
            name: 'Top Agua Ahorrada',
            component: TopAhorroAguaView
        },
        {
            path: '/historial',
            name: 'Mis Duchas',
            component: Historial
        },
        {
            path: "*",
            name: 'NotFound',
            component: NotFound
        }
    ]
});