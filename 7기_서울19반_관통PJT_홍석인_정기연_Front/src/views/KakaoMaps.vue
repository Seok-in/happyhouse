<template>
  <v-container id="house-view" fluid tag="section">
    <v-row>
      <v-col cols="12" md="12">
        <material-card color="blue" icon-fit>
          <template #heading>
            <div class="pa-8 white--text">
              <div class="text-h4 font-weight-light">아파트 주변 정보</div>
              <div class="text-caption">Map</div>
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
                <kakaomap />
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
import Kakaomap from "../components/map/KakaoMap.vue";

export default {
  name: "KakaoMapView",
  components: {
    Kakaomap,
    HouseSearchBar,
    HouseList,
  },
};
</script>
