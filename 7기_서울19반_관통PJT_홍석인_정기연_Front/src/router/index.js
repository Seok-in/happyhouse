// Imports
import Vue from "vue";
import Router from "vue-router";
import { trailingSlash } from "@/util/helpers";
import { layout, route } from "@/util/routes";

Vue.use(Router);

const router = new Router({
  mode: "history",
  base: process.env.BASE_URL,
  scrollBehavior: (to, from, savedPosition) => {
    if (to.hash) return { selector: to.hash };
    if (savedPosition) return savedPosition;

    return { x: 0, y: 0 };
  },
  routes: [
    layout("Default", [
      {
        name: "Dashboard",
        path: "",
        redirect: "",
        component: () => import("@/views/Dashboard.vue"),
        children: [
          {
            path: "",
            name: "boardList",
            component: () => import("@/components/board/BoardList.vue"),
          },
          {
            path: "detail/:articleno",
            name: "boardDetail",
            component: () => import("@/components/board/BoardDetail.vue"),
          },
          {
            path: "modify/:articleno",
            name: "boardModify",
            component: () => import("@/components/board/BoardModify.vue"),
          },
          {
            path: "register",
            name: "boardRegister",
            component: () => import("@/components/board/BoardRegister.vue"),
          },
          {
            path: "delete/:articleno",
            name: "boardDelete",
            component: () => import("@/components/board/BoardDelete.vue"),
          },
        ],
      },
      {
        path: "/kakaologin",
        name: "kakaologin",
        component: () => import("@/views/KakaoLogin.vue"),
      },
      {
        name: "house",
        path: "/house",
        redirect: "/house",
        component: () => import("@/views/HouseView.vue"),
        children: [
          {
            path: "",
            name: "houseList",
            component: () => import("@/components/house/HouseList.vue"),
          },
        ],
      },
      {
        path: "/maps",
        name: "kakaoMap",
        component: () => import("@/views/KakaoMaps.vue"),
      },
      {
        name: "worldcup",
        path: "/worldcup",
        redirect: "/worldcup",
        component: () => import("@/views/WorldCup.vue"),
        children: [
          {
            path: "",
            name: "houseList",
            component: () => import("@/components/house/HouseList.vue"),
          },
        ],
      },
      {
        path: "/findpw",
        name: "findPW",
        component: () => import("@/views/Find.vue"),
      },
      // Pages
      route("UserProfile", null, "components/profile"),

      // Components
      route("Notifications", null, "components/notifications"),
      route("Icons", null, "components/icons"),
      route("Typography", null, "components/typography"),
      // Tables
      route("Regular Tables", null, "tables/regular"),
      // Maps
      route("Google Maps", null, "maps/google"),
      route("Login", null, "login"),
      route("Sign Up", null, "signup"),
    ]),
  ],
});

// router.beforeEach((to, from, next) => {
//   return to.path.endsWith("/") ? next() : next(trailingSlash(to.path));
// });

export default router;
