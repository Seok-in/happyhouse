<template>
  <v-menu
    v-if="logined"
    bottom
    left
    min-width="200"
    offset-y
    origin="top right"
    transition="scale-transition"
  >
    <template v-slot:activator="{ attrs, on }">
      <v-btn class="ml-2" min-width="0" text v-bind="attrs" v-on="on">
        <v-icon>mdi-account</v-icon>
      </v-btn>
    </template>

    <v-list :tile="false" flat nav>
      <app-bar-item>
        <v-list-item-title @click="goProfile()" v-text="profile" />
      </app-bar-item>
      <app-bar-item>
        <v-list-item-title @click="loged()" v-text="logout" />
      </app-bar-item>
    </v-list>
  </v-menu>
  <v-menu
    v-else
    bottom
    left
    min-width="200"
    offset-y
    origin="top right"
    transition="scale-transition"
  >
    <template v-slot:activator="{ attrs, on }">
      <v-btn
        class="ml-2"
        min-width="0"
        text
        v-bind="attrs"
        to="/login"
        v-on="on"
      >
        <v-icon>mdi-account-check</v-icon>
      </v-btn>
    </template>
  </v-menu>
</template>

<script>
import VueCookies from "vue-cookies";
import http from "../../../api/http";

export default {
  name: "DefaultAccount",
  data() {
    return {
      profile: "Profile",
      logout: "LogOut",
      logined: this.$cookies.get("JSESSIONID"),
    };
  },
  methods: {
    goProfile() {
      location.replace("http://localhost:8080/components/profile");
    },
    loged() {
      http.post(`/logout`).then(({ data }) => {
        if (data === "ok") {
          alert("로그아웃 되었습니다.");
          this.$cookies.remove("JSESSIONID");
          location.reload();
        } else {
          alert("로그아웃 실패..!");
        }
      });
    },
  },
};
</script>
