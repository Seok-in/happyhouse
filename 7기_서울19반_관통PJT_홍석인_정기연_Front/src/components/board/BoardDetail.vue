<template>
  <v-card>
    <v-container>
      <v-row>
        <v-col cols="6">
          <v-card elevation="3" height="80%">
            <v-card-title class="font-weight-bold text-h5">
              <v-col cols="2" class="text-md-center transition"> 제목 : </v-col>
              <v-col cols="10" class="text-md-center">
                {{ article.subject }}
              </v-col>
            </v-card-title>
          </v-card>
        </v-col>
        <v-col cols="6">
          <v-card elevation="3" height="80%">
            <v-card-title class="font-weight-bold text-h5">
              <v-col class="text-md-center">
                작성자 : {{ article.userid }}
              </v-col>
            </v-card-title>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <v-card elevation="3">
            <v-card-title>
              <v-col cols="2" class="text-md-center font-weight-bold text-h5">
                내용 :
              </v-col>
              <v-col cols="10" class="text-h5">
                {{ article.content }}
              </v-col>
            </v-card-title>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="2">
          <v-btn class="text-h5 font-weight-bold" @click="listArticle">
            목록으로 가기
          </v-btn>
        </v-col>
        <v-col v-if="article.userid === id" cols="2">
          <v-btn class="text-h5 font-weight-bold" @click="moveModifyArticle">
            수정하기
          </v-btn>
        </v-col>
        <v-col v-if="article.userid === id" cols="2">
          <v-btn class="text-h5 font-weight-bold" @click="deleteArticle">
            삭제하기
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
// import moment from "moment";
import http from "@/api/http";

export default {
  name: "BoardDetail",
  data() {
    return {
      id: "",
      article: {},
    };
  },
  computed: {
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
      console.log(data);
    });
    this.userInfo();
  },
  methods: {
    listArticle() {
      this.$router.push({ name: "boardList" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "boardModify",
        params: { articleno: this.article.articleno },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "boardDelete",
          params: { articleno: this.article.articleno },
        });
      }
    },
    userInfo() {
      http
        .post(`/user`, null, {
          Headers: {
            Authorization: sessionStorage.getItem("accessToken"),
          },
        })
        .then(({ data }) => {
          this.id = data.id;
          this.name = data.name;
        });
    },
  },
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD hh:mm:ss");
  //   },
  // },
};
</script>

<style></style>
