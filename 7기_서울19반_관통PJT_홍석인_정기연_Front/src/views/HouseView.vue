<template>
  <v-container id="house-view" fluid tag="section">
    <v-row>
      <v-col cols="12" md="12">
        <material-card color="blue" icon-fit>
          <template #heading>
            <div class="pa-8 white--text">
              <div class="text-h4 font-weight-light">아파트 거래 리스트</div>
              <div class="text-caption">Notice Board</div>
            </div>
          </template>
          <v-container>
            <v-row>
              <v-col>
                <house-search-bar />
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="6">
                <house-list />
              </v-col>
              <v-col cols="6">
                <house-detail />
              </v-col>
            </v-row>
          </v-container>
        </material-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";

export default {
  name: "HouseView",
  components: {
    HouseSearchBar,
    HouseList,
    HouseDetail,
  },
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 149, 27, 0.3) 30%
  );
}
</style>
