<template>
  <!-- Login Component -->
  <v-container style="max-width: 450px">
    <v-row>
      <v-col>
        <v-card>
          <div class="pa-10">
            <h1 style="text-align: center" class="mb-10">FindPW</h1>
            <form>
              <v-text-field
                v-model="id"
                label="ID"
                prepend-inner-icon="mdi-account"
              ></v-text-field>
              <v-text-field
                v-model="email"
                prepend-inner-icon="mdi-lock"
                label="Email"
              >
              </v-text-field>
              <v-btn
                color="pink lighten-1 text-capitalize"
                depressed
                outlined
                block
                large
                dark
                class="mb-3 mr-3"
                @click="findPW"
              >
                Find Password
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
  name: "FindPWView",
  data() {
    return {
      id: "",
      email: "",
    };
  },
  methods: {
    findPW() {
      http
        .post(`/find`, {
          id: this.id,
          email: this.email,
        })
        .then(({ data }) => {
          if (data === "fail") {
            alert("해당되는 계정이 없습니다.");
          } else {
            alert("비밀번호는 " + data + " 입니다.");
            window.location.replace("http://localhost:8080");
          }
        });
    },
  },
};
</script>
