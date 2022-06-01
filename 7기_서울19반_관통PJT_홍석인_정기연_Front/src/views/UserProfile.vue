<template v-if="logined">
  <v-container style="max-width: 1000px">
    <v-row>
      <v-col>
        <v-card>
          <div class="pa-10">
            <h1 style="text-align: center" class="mb-10">User Profile</h1>
            <form>
              <v-text-field
                v-model="id"
                label="ID"
                prepend-inner-icon="mdi-account"
                disabled
              ></v-text-field>
              <v-text-field
                v-model="password"
                type="password"
                label="PASSWORD"
                prepend-inner-icon="mdi-lock"
              ></v-text-field>
              <v-text-field
                v-model="email"
                label="Email"
                prepend-inner-icon="mdi-account-key"
              ></v-text-field>
              <v-text-field
                v-model="name"
                label="Name"
                prepend-inner-icon="mdi-alphabetical"
              ></v-text-field>
              <v-btn
                color="pink lighten-1 text-capitalize"
                depressed
                large
                block
                dark
                class="mb-3"
                @click="update"
              >
                Update Profile
              </v-btn>
              <v-btn
                color="pink lighten-1 text-capitalize"
                depressed
                large
                block
                dark
                @click="memberDelete"
              >
                Member Delete
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
  name: "UserProfileView",
  data() {
    return {
      id: "",
      email: "",
      name: "",
      password: "",
      logined: this.$cookies.get("JSESSIONID"),
    };
  },

  created() {
    const loginData = this.$cookies.get("JSESSIONID");
    if (!this.logined) {
      alert("로그인을 해주세요!");
      location.href = "http://localhost:8080/login";
    }
    // kakaoLogin User
    if (loginData && loginData.includes(".")) {
      alert("hey");
      // eslint-disable-next-line no-undef
      const idJwt = loginData;
      const base64Url = idJwt.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split("")
          .map(function (c) {
            return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
          })
          .join("")
      );
      const obj = JSON.parse(jsonPayload);
      this.id = obj.userId;
      this.email = obj.userId;
    } else {
      http
        .post(`/user`, null, {
          Headers: {
            Authorization: sessionStorage.getItem("accessToken"),
          },
        })
        .then(({ data }) => {
          this.id = data.id;
          this.email = data.email;
          this.name = data.name;
        });
    }
  },

  methods: {
    update() {
      if (
        ValidateEmail(this.email) &&
        this.name.replace(/\s/g, "") !== "" &&
        ValidatePassword(this.password) &&
        this.password.search(/\s/) === -1 &&
        this.email.search(/\s/) === -1 &&
        this.name.search(/\s/) === -1
      ) {
        http
          .post(`/memberUpdate`, {
            id: this.id,
            pw: this.password,
            email: this.email,
            name: this.name,
          })
          .then(({ data }) => {
            if (data === "ok") {
              alert("수정되었습니다. 다시 로그인해주세요!");
              this.$cookies.remove("JSESSIONID");
              location.replace("http://localhost:8080/login");
            } else {
              alert("유저 정보 수정 실패..!");
            }
          });
      } else if (!ValidateEmail(this.email)) {
        alert("이메일 양식을 확인해주세요");
      } else if (this.name.replace(/\s/g, "") === "") {
        alert("이름을 입력하세요");
      } else if (!ValidatePassword(this.password)) {
        alert(
          "비밀번호는 8자 ~ 25자 소문자와 대문자, 숫자, 특수기호를 포함해야합니다."
        );
      } else if (this.password.search(/\s/) !== -1) {
        alert("비밀번호에는 공백이 포함될 수 없습니다.");
      } else if (this.email.search(/\s/) !== -1) {
        alert("EMAIL에는 공백이 포함될 수 없습니다.");
      } else if (this.name.search(/\s/) !== -1) {
        alert("이름에는 공백이 포함될 수 없습니다.");
      }
      function ValidateEmail(mail) {
        if (
          mail !== "" &&
          /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/.test(
            mail
          )
        ) {
          return true;
        }
        return false;
      }
      function ValidatePassword(pw) {
        if (
          pw !== "" &&
          /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/.test(
            pw
          )
        ) {
          return true;
        }
        return false;
      }
    },
    memberDelete() {
      http
        .post(`/memberUpdate`, {
          id: this.id,
        })
        .then(({ data }) => {
          if (data === "ok") {
            alert("탈퇴 완료되었습니다.");
            this.$cookies.remove("JSESSIONID");
            location.replace("http://localhost:8080");
          } else {
            alert("탈퇴 실패..!");
          }
        });
    },
  },
};
</script>
