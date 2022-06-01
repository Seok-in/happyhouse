import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import "./plugins";
import store from "./store";
import { sync } from "vuex-router-sync";
import vcookie from "vue-cookies";
Vue.use(vcookie);

Vue.config.productionTip = false;

sync(store, router);

new Vue({
  router,
  vuetify,
  store,
  vcookie,
  render: (h) => h(App),
}).$mount("#app");
