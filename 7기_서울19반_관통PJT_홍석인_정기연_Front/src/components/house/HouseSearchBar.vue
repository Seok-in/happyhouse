<template>
  <v-row class="text-center">
    <!-- <b-col class="sm-3">
      <b-form-input
        v-model.trim="dongCode"
        placeholder="동코드 입력...(예 : 11110)"
        @keypress.enter="sendKeyword"
      ></b-form-input>
    </b-col>
    <b-col class="sm-3" align="left">
      <b-button variant="outline-primary" @click="sendKeyword">검색</b-button>
    </b-col> -->
    <v-col class="sm-3">
      <v-select
        v-model="sidoCode"
        :items="sidos"
        @change="gugunList"
      ></v-select>
    </v-col>
    <v-col class="sm-3">
      <v-select
        v-model="gugunCode"
        :items="guguns"
        @change="searchApt"
      ></v-select>
    </v-col>
  </v-row>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
    };
  },
  computed: {
    ...mapState("house", ["sidos", "guguns", "houses", "sidoStr", "gugunStr"]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.getSido();
    console.log(this.sidoCode);
  },
  methods: {
    ...mapActions("house", ["getSido", "getGugun", "getHouseList"]),
    ...mapMutations("house", ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST"]),
    // sidoList() {
    //   this.getSido();
    // },
    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    searchApt() {
      console.log(this.gugunCode);
      if (this.gugunCode) this.getHouseList(this.gugunCode);
    },
  },
};
</script>

<style></style>
