<template>
  <div>{{ $route.query.code }}</div>
</template>
<script>
import http from "../api/http";

export default {
  name: "KaKaoLoginView",
  data() {
    return {
      code: this.$route.query.code,
    };
  },
  created() {
    console.log(this.$route.query);
    console.log(this.$route.params);
    console.log(this.$route.query.code);
    console.log("HI");
    http
      .get("oauth", { params: { code: this.$route.query.code } })
      .then(({ data }) => {
        if (data === "fail") {
          alert("카카오 로그인 실패");
        } else {
          alert("카카오 로그인 성공");
          this.$cookies.set("JSESSIONID", data);
          location.replace("http://localhost:8080");
        }
      });
  },
};
</script>
