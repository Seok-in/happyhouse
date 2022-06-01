<template>
  <v-container>
    <v-container>
      <v-row>
        <v-col cols="8">
          <v-text-field v-model="house.아파트" readonly label="아파트 이름" />
        </v-col>
        <v-col cols="4">
          <v-text-field v-model="house.일련번호" readonly label="일련 번호" />
        </v-col>
      </v-row>
    </v-container>
    <v-container>
      <v-row>
        <v-col cols="4">
          <v-text-field v-model="house.법정동" readonly label="법정동" />
        </v-col>
        <v-col cols="4">
          <v-text-field v-model="house.층" readonly label="층" />
        </v-col>
        <v-col cols="4">
          <v-text-field
            v-model="house.거래금액"
            readonly
            label="거래 금액(만원)"
          />
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "HouseDetail",
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  computed: {
    ...mapState("house", ["house"]),
    // house() {
    //   return this.$store.state.house;
    // },
  },
};
</script>

<style></style>
