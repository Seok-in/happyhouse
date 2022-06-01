<template>
  <v-card>
    <v-container>
      <v-data-table
        :headers="headers"
        :items="articles"
        :loading="loading"
        loading-text="Loading... Please wait"
        :page.sync="page"
        :items-per-page="itemsPerPage"
        hide-default-footer
        class="elevation-1"
        @click:row="showDetail"
        @page-count="pageCount = $event"
      >
      </v-data-table>
      <div class="text-center pt-2">
        <v-pagination
          v-model="page"
          :length="pageCount"
          :total-visible="totalVisible"
          next-icon="mdi-menu-right"
          prev-icon="mdi-menu-left"
        ></v-pagination>
      </div>
      <v-btn v-if="id" cols="12" x-large @click="moveWrite"> 글 작성 </v-btn>
    </v-container>
  </v-card>
</template>

<script>
import http from "@/api/http";

export default {
  name: "BoardList",
  data: () => ({
    id: "",
    name: "",
    page: 1,
    pageCount: 0,
    itemsPerPage: 5,
    totalVisible: 10,
    loading: false,
    headers: [
      {
        sortable: false,
        text: "글번호",
        value: "no",
      },
      {
        sortable: false,
        text: "글 제목",
        value: "title",
      },
      {
        sortable: false,
        text: "글쓴이",
        value: "writer",
        align: "right",
      },
      {
        sortable: false,
        text: "업로드 시간",
        value: "date",
        align: "right",
      },
    ],
    articles: [],
  }),
  created() {
    http.get(`/board`).then(({ data }) => {
      data.forEach((temp) => {
        this.articles.push({
          no: temp.articleno,
          title: temp.subject,
          writer: temp.userid,
          date: temp.regtime,
        });
      });
    });
    if (this.$cookies.get("JSESSIONID")) {
      this.userInfo();
    }
  },
  methods: {
    showDetail(event, { item }) {
      console.log(item);
      console.log(this.$cookies.get("JSESSIONID"));
      if (this.$cookies.get("JSESSIONID")) {
        this.$router.push({
          name: "boardDetail",
          params: { articleno: item.no },
        });
      }
    },
    moveWrite() {
      this.$router.push({ name: "boardRegister" });
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
};
</script>

<style></style>
