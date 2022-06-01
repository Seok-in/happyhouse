<template>
  <v-container v-if="houses && houses.length != 0" class="text-center">
    <v-data-table
      :headers="headers"
      :items="houses"
      :loading="loading"
      loading-text="Loading... Please wait"
      :page.sync="page"
      :items-per-page="itemsPerPage"
      hide-default-footer
      class="elevation-1"
      @click:row="selectHouse"
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
      >
      </v-pagination>
    </div>
  </v-container>
  <v-container v-else class="text-center">
    <v-row>
      <v-col><v-alert type="warning">주택 목록이 없습니다.</v-alert></v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";
import store from "../../store";

export default {
  name: "HouseList",
  data() {
    return {
      objects: [],
      page: 1,
      pageCount: 0,
      itemsPerPage: 5,
      totalVisible: 10,
      loading: true,
      headers: [
        {
          sortable: false,
          text: "아파트",
          value: "아파트",
        },
        {
          sortable: false,
          text: "일련번호",
          value: "일련번호",
          align: "right",
        },
        {
          sortable: false,
          text: "거래금액",
          value: "거래금액",
          align: "right",
        },
      ],
    };
  },
  computed: {
    ...mapState("house", ["house", "houses"]),
    // houses() {
    //   return this.$store.state.houses;
    // },
  },
  methods: {
    ...mapActions("house", ["detailHouse"]),
    ...mapActions("map", ["getStr", "getAddr"]),
    ...mapGetters("map", ["getAddress", "getterAddr"]),
    selectHouse(event, { item }) {
      console.log(item);
      // console.log(house);
      this.detailHouse(item);
      this.getStr({ dong: item.법정동, jibun: item.지번 });
      console.log(this.getAddress());
      this.getAddr(this.getAddress());
      console.log(this.getterAddr());
    },
  },
};
</script>

<style></style>
