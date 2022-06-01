<template>
  <div>
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
    <div id="map" style="width: 100%; height: 350px"></div>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";

export default {
  name: "HouseList2",
  data() {
    return {
      map: "", // 지도 객체
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
  // mounted() {
  //   window.kakao && window.kakao.maps
  //     ? this.initMap()
  //     : this.addKakaoMapScript();
  // },

  methods: {
    ...mapActions("house", ["detailHouse"]),
    ...mapActions("map", ["getStr"]),
    ...mapGetters("house", ["returnHousesNum"]),
    ...mapGetters("map", ["getAddress"]),
    selectHouse(event, { item }) {
      console.log(item);
      // console.log(house);
      this.detailHouse(item);
      this.getStr({ dong: item.법정동, jibun: item.지번 });
      console.log(this.getAddress());
      console.log(this.returnHousesNum());
      // 주소-좌표 변환 객체를 생성합니다
      // var geocoder = () => kakao.maps.services.Geocoder();
      // console.log(geocoder);
      // // 주소로 좌표를 검색합니다
      // geocoder.addressSearch(this.getAddress(), function (result, status) {
      //   // 정상적으로 검색이 완료됐으면
      //   if (status === this.$kakao.maps.services.Status.OK) {
      //     var coords = this.$kakao.maps.LatLng(result[0].y, result[0].x);
      //     console.log(coords.Ma);
      //     console.log(coords.La);
      //   }
      // });
    },
    // addKakaoMapScript() {
    //   const script = document.createElement("script");
    //   script.onload = new window.kakao.maps.load(this.initMap);
    //   script.src =
    //     "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8f5222bad0724268fd9320ee91df6988&libraries=services";
    //   document.head.appendChild(script);
    // },
    // initMap() {
    //   var container = document.getElementById("map");
    //   var options = {
    //     center: new kakao.maps.LatLng(35.3351575394625, 129.01074046469583),
    //     level: 2,
    //   };

    //   var map = new kakao.maps.Map(container, options);
    //   var markerPosition = new kakao.maps.LatLng(
    //     35.3351575394625,
    //     129.01074046469583
    //   ); //마커 표시 위치
    //   var marker = new kakao.maps.Marker({
    //     position: markerPosition,
    //   });
    //   marker.setMap(map);
    // },
  },
};
</script>

<style></style>
