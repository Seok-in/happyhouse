<template>
  <v-container class="bv-example-row mt-3">
    <v-row>
      <v-col>
        <v-alert border="top" dense prominent shaped type="warning"
          >삭제 처리 중...
        </v-alert>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import http from "@/api/http";

export default {
  name: "BoardDelete",
  created() {
    http.delete(`/board/${this.$route.params.articleno}`).then(({ data }) => {
      let msg = "삭제 처리시 문제가 발생했습니다.";
      if (data === "success") {
        msg = "삭제가 완료되었습니다.";
      }
      alert(msg);
      // 현재 route를 /list로 변경.
      this.$router.push({ name: "boardList" });
    });
  },
};
</script>

<style></style>
