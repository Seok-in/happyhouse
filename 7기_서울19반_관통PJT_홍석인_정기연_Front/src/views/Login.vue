<template>
  <!-- Login Component -->
  <v-container style="max-width: 450px">
    <v-row>
      <v-col>
        <v-card>
          <div class="pa-10">
            <h1 style="text-align: center" class="mb-10">Login</h1>
            <form>
              <v-text-field
                v-model="id"
                label="ID"
                prepend-inner-icon="mdi-account"
              ></v-text-field>
              <v-text-field
                v-model="password"
                prepend-inner-icon="mdi-lock"
                type="password"
                label="Password"
              >
              </v-text-field>
              <v-btn
                color="pink lighten-1 text-capitalize"
                depressed
                outlined
                large
                dark
                class="mb-3 mr-3"
                @click="SignUp"
              >
                Sign Up
              </v-btn>
              <v-btn
                color="pink lighten-1 text-capitalize"
                depressed
                outlined
                large
                dark
                class="mb-3 mr-3"
                @click="Login"
                ><v-icon left>mdi-account-circle</v-icon>
                Login
              </v-btn>
              <v-btn
                color="pink lighten-1 text-capitalize"
                depressed
                large
                outlined
                dark
                class="mb-3"
                @click="Find"
              >
                Find PW
              </v-btn>
              <v-btn
                color="green darken-1 text-capitalize"
                depressed
                large
                block
                dark
                outlined
                @click="KakaoLogin"
                ><v-icon left>mdi-android</v-icon>
                Kakao Login
              </v-btn>
            </form>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import http from "../api/http";

export default {
  name: "LoginView",
  data() {
    return {
      id: "",
      password: "",
    };
  },
  methods: {
    Login() {
      http
        .post(`/login`, {
          id: this.id,
          pw: this.password,
        })
        .then(({ data }) => {
          if (data === "Fail") {
            alert("값이 제대로 전달되지 않았습니다. 다시 시도해주세요.");
          } else if (data === "Login Fail") {
            alert("아이디 혹은 비밀번호를 확인해주세요");
          } else {
            alert("로그인 완료!");
            this.$cookies.set("JSESSIONID", data.JSESSIONID);
            sessionStorage.setItem("accessToken", data.JWT);
            location.replace("http://localhost:8080/");
          }
        });
    },
    Find() {
      this.$router.push("/findpw");
    },
    SignUp() {
      this.$router.push("/signup");
    },
    KakaoLogin() {
      window.location.href =
        "https://kauth.kakao.com/oauth/authorize?client_id=e59460e1d3585d72592c6c057c710bbc&redirect_uri=http://localhost:8080/kakaologin&response_type=code";
    },
  },
};
</script>
