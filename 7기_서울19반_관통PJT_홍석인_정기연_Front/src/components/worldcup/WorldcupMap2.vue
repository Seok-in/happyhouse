<template>
  <div>
    <v-container>
      <v-card>
        <v-row justify="center">
          <div id="map2"></div>
        </v-row>
      </v-card>
      <v-row justify="center">
        <v-col cols="3">
          <v-btn
            color="secondary"
            dark
            rounded
            class="mr-3"
            @click="clickFunction(position1)"
            >search</v-btn
          >
        </v-col>
        <v-col cols="3">
          <v-btn
            color="error"
            dark
            rounded
            class="mr-3"
            @click="Marking(School, position1)"
            >School</v-btn
          >
        </v-col>
        <v-col cols="3">
          <v-btn
            color="primary"
            dark
            rounded
            class="mr-3"
            @click="Marking(Food, position1)"
            >Food</v-btn
          >
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="3"
          ><v-btn
            color="success"
            dark
            rounded
            class="mr-3"
            @click="Marking(Cafe, position1)"
            >Cafe</v-btn
          ></v-col
        >
        <v-col cols="3"
          ><v-btn
            color="warning"
            dark
            rounded
            class="mr-3"
            @click="Marking(Subway, position1)"
            >Subway</v-btn
          ></v-col
        >
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "WorldcupMap2",
  props: {
    key: Number,
  },
  data() {
    return {
      markers: [],
      position1: [[33.450701, 126.570667]],
      School: "SC4",
      Food: "FD6",
      Cafe: "CE7",
      Subway: "SW8",
      infowindows: [],
      address: "",
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=915cffed372954b7b44804ed422b9cf0&libraries=services&event";
      /* global kakao */
      script.addEventListener("load", () => {
        console.log("loaded", kakao);
      });
      document.head.appendChild(script);
    }
  },

  methods: {
    ...mapGetters("map", ["getterAddr2"]),
    initMap() {
      const container = document.getElementById("map2");
      container.style.width = "400px";
      container.style.height = "300px";
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 2,
      };
      this.map = new kakao.maps.Map(container, options);
      this.getPosition();
      setTimeout(() => {
        this.displayMarker(this.position1);
      }, 300);
    },
    clickFunction(markerPositions) {
      this.getPosition();
      setTimeout(() => {
        this.displayMarker(this.position1);
      }, 300);
    },
    getPosition() {
      var geocoder = new kakao.maps.services.Geocoder();
      geocoder.addressSearch(this.getterAddr2(), (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          this.position1 = [[coords.Ma, coords.La]];
          console.log("HI1");
        }
      });
    },
    displayMarker(markerPositions2) {
      console.log("HI2");
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }
      if (this.infowindows.length > 0) {
        this.infowindows.forEach((infowindow) => infowindow.close());
      }
      const positions = markerPositions2.map(
        (position) => new kakao.maps.LatLng(...position)
      );

      if (positions.length > 0) {
        this.markers = positions.map(
          (position) =>
            new kakao.maps.Marker({
              map: this.map,
              position,
            })
        );

        const bounds = positions.reduce(
          (bounds, markerPositions2) => bounds.extend(markerPositions2),
          new kakao.maps.LatLngBounds()
        );
        this.map.setBounds(bounds);
      }
    },
    Marking(Code, markerPosition) {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }
      if (this.infowindows.length > 0) {
        this.infowindows.forEach((infowindow) => infowindow.close());
      }

      const ps = new kakao.maps.services.Places(this.map);
      console.log(markerPosition);
      ps.categorySearch(Code, this.placesSearchCB, {
        location: new kakao.maps.LatLng(
          markerPosition[0][0],
          markerPosition[0][1]
        ),
        SORT_BY: "DISTANCE",
        page: 1,
        size: 5,
      });
    },

    placesSearchCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        var length = 0;
        while (data.length > length) {
          console.log(data);
          this.displayMarkerData(data[length]);
          length += 1;
        }
      }
    },
    displayMarkerData(place) {
      var infowindow = new kakao.maps.InfoWindow({
        zIndex: 1,
        content:
          '<div style="padding:5px;font-size:12px;"><a href="' +
          place.place_url +
          '">' +
          place.place_name +
          "</a></div>",
      });
      var marker = new kakao.maps.Marker({
        map: this.map,
        position: new kakao.maps.LatLng(place.y, place.x),
      });
      this.markers.push(marker);
      this.infowindows.push(infowindow);
      infowindow.open(this.map, marker);
    },
  },
};
</script>
