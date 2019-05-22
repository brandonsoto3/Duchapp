import './plugins/bootstrap';
import Vue from 'vue';
import './plugins/vuetify'
import App from '@/js/App.vue'
import router from '@/js/routes.js';
import Vuelidate from 'vuelidate';
import VueResource from 'vue-resource';
import VueSession from 'vue-session';
import VueTimeago from 'vue-timeago'
import VueApexCharts from 'vue-apexcharts'
Vue.use(VueApexCharts)

Vue.component('apexchart', VueApexCharts);

Vue.use(VueTimeago, {
  name: 'Timeago',
  locale: 'es',
  locales: {
    'zh-CN': require('date-fns/locale/zh_cn'),
  }
})

Vue.use(VueSession)
Vue.use(Vuelidate);
Vue.use(VueResource);

Vue.config.productionTip = false;

const app = new Vue({
    el: '#app',
    router,
    render: h => h(App), 
});

export default app;