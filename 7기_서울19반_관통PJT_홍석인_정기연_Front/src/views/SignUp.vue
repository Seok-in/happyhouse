<template>
  <!-- Login Component -->
  <v-container style="max-width: 450px">
    <v-row>
      <v-col>
        <v-card>
          <div class="pa-10">
            <h1 style="text-align: center" class="mb-10">Sign Up</h1>
            <form>
              <v-text-field
                v-model="id"
                label="ID"
                prepend-inner-icon="mdi-account"
              ></v-text-field>
              <v-btn
                color="pink lighten-1 text-capitalize"
                depressed
                dark
                class="mb-3"
                @click="Check"
                >Check</v-btn
              >
              <v-text-field
                v-model="password"
                prepend-inner-icon="mdi-lock"
                type="password"
                label="Password"
              >
              </v-text-field>
              <v-text-field
                v-model="password2"
                prepend-inner-icon="mdi-lock"
                type="password"
                label="Password Check"
              >
              </v-text-field>
              <v-text-field
                v-model="email"
                label="Email"
                prepend-inner-icon="mdi-account"
              ></v-text-field>
              <v-text-field
                v-model="name"
                label="Name"
                prepend-inner-icon="mdi-account"
              ></v-text-field>
              <v-btn
                color="pink lighten-1 text-capitalize"
                depressed
                large
                block
                dark
                @click="SignUp"
              >
                Sign Up
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
  name: "SignUpView",
  data() {
    return {
      id: "",
      password: "",
      password2: "",
      email: "",
      name: "",
      checked: false,
    };
  },
  methods: {
    Check() {
      if (this.id.search(/\s/) !== -1) {
        alert("아이디에는 공백이 포함될 수 없습니다.");
      } else {
        http
          .post(`/check`, {
            id: this.id,
          })
          .then(({ data }) => {
            if (data === "fail") {
              alert("사용중인 아이디 입니다!");
            } else if (data === "error") {
              alert("오류가 발생하였습니다.");
            } else {
              alert("사용가능한 아이디 입니다.");
              this.checked = true;
            }
          });
      }
    },
    SignUp() {
      if (
        this.password === this.password2 &&
        this.checked === true &&
        ValidateEmail(this.email) &&
        this.name.replace(/\s/g, "") !== "" &&
        ValidatePassword(this.password) &&
        this.password.search(/\s/) === -1 &&
        this.id.search(/\s/) === -1 &&
        this.email.search(/\s/) === -1 &&
        this.name.search(/\s/) === -1
      ) {
        http
          .post(`/signup`, {
            id: this.id,
            pw: this.password,
            email: this.email,
            name: this.name,
          })
          .then(({ data }) => {
            if (data === "ok") {
              alert("회원가입이 완료되었습니다!");
              this.$router.push("/login");
            } else {
              alert("회원가입에 실패하였습니다.");
            }
          });
      } else if (this.checked === false) {
        alert("ID 중복확인을 해주세요!");
      } else if (this.password !== this.password2) {
        alert("비밀번호를 확인해주세요");
      } else if (!ValidateEmail(this.email)) {
        alert("이메일 양식을 확인해주세요");
      } else if (this.name.replace(/\s/g, "") === "") {
        alert("이름을 입력하세요");
      } else if (!ValidatePassword(this.password)) {
        alert(
          "비밀번호는 8자 ~ 25자 소문자와 대문자, 숫자, 특수기호를 포함해야합니다."
        );
      } else if (this.id.search(/\s/) !== -1) {
        alert("아이디에는 공백이 포함될 수 없습니다.");
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
  },
};
</script>
