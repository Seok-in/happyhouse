<template>
  <v-card>
    <v-form @submit="onSubmit" @reset="onReset">
      <v-container>
        <v-card elevation="3">
          <v-container>
            <v-row>
              <v-col cols="3">
                <v-text-field
                  v-if="type === 'modify'"
                  v-model="article.userid"
                  readonly
                  label="작성자"
                />
                <v-text-field
                  v-else
                  v-model="id"
                  else
                  readonly
                  label="작성자"
                />
              </v-col>
            </v-row>
          </v-container>
        </v-card>

        <v-card elevation="3">
          <v-container>
            <v-row>
              <v-col cols="5">
                <v-text-field v-model="article.subject" label="제목" />
              </v-col>
            </v-row>
          </v-container>
        </v-card>
        <v-card elevation="3">
          <v-container>
            <v-row id="content-group">
              <v-col cols="2" sm="12">
                <v-textarea v-model="article.content" label="내용"></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-card>
        <v-row>
          <v-col cols="1" sm="2">
            <v-btn v-if="type === 'register'" class="text-center" type="submit">
              등록 완료
            </v-btn>
            <v-btn v-if="type === 'modify'" class="text-center" type="submit">
              수정 완료
            </v-btn>
          </v-col>
          <v-col cols="2" sm="2">
            <v-btn type="reset" class="text-center" @click="moveList">
              초기화
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </v-card>
</template>

<script>
import http from "@/api/http";
export default {
  name: "BoardInputItem",
  props: {
    type: { type: String },
  },
  data() {
    return {
      id: "",
      article: {
        articleno: 0,
        userid: "",
        subject: "",
        content: "",
      },
      isUserid: false,
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
    if (this.type === "modify") {
      http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
        // this.article.articleno = data.article.articleno;
        // this.article.userid = data.article.userid;
        // this.article.subject = data.article.subject;
        // this.article.content = data.article.content;
        this.article = data;
        console.log("inputitem : ");
        console.log(this.article);
      });
      this.isUserid = true;
    }
    this.userInfo();
  },

  methods: {
    userInfo() {
      http
        .post(`/user`, null, {
          Headers: {
            Authorization: sessionStorage.getItem("accessToken"),
          },
        })
        .then(({ data }) => {
          this.id = data.id;
        });
    },
    onSubmit(event) {
      event.preventDefault();
      let err = true;
      let msg = "";
      if (!this.article.subject) {
        msg = "제목 입력해주세요";
        err = false;
        this.$refs.subject.focus();
      }
      if (!this.article.content) {
        msg = "내용 입력해주세요";
        err = false;
        this.$refs.content.focus();
      }
      if (!err) alert(msg);
      else
        this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
      this.$router.push({ name: "boardList" });
    },
    registArticle() {
      http
        .post(`/board`, {
          userid: this.id,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    modifyArticle() {
      http
        .put(`/board/${this.article.articleno}`, {
          articleno: this.article.articleno,
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "boardList" });
        });
    },
    moveList() {
      this.$router.push({ name: "boardList" });
    },
  },
};
</script>

<style scoped></style>
