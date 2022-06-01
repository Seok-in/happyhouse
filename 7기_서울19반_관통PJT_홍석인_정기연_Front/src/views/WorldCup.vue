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
              <v-col cols="1" sm="10">
                <house-search-bar />
              </v-col>
              <v-col>
                <v-btn @click="makeRound">확인</v-btn>
              </v-col>
            </v-row>
            <v-row v-if="round === 0">
              <v-select
                v-model="round"
                :items="rounds"
                @change="readyGame"
              ></v-select>
            </v-row>
            <v-row v-else-if="round >= 1">
              <v-col>{{ cnt + 1 }}/{{ round }}</v-col>
            </v-row>
            <v-row v-if="round > 0" justify="center">
              <v-col cols="6">
                <v-row justify="center">
                  <worldcup-map-1 :key="key1" />
                  <worldcup-detail :house="h1" />
                  <v-btn v-if="!(round < 1 && round > 0)" @click="click1">
                    요거
                  </v-btn>
                </v-row>
              </v-col>
              <v-col v-if="round >= 1" cols="6">
                <v-row justify="center">
                  <worldcup-map-2 :key="key2" />
                  <worldcup-detail :house="h2" />
                  <v-btn @click="click2"> 이거 </v-btn>
                </v-row>
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
import WorldcupDetail from "@/components/worldcup/WorldcupDetail.vue";
import { mapActions, mapGetters } from "vuex";
import HOUSE from "@/store/modules/house";
import WorldcupMap1 from "../components/worldcup/WorldcupMap1.vue";
import WorldcupMap2 from "../components/worldcup/WorldcupMap2.vue";
export default {
  name: "HouseView",
  components: {
    HouseSearchBar,
    WorldcupDetail,
    WorldcupMap1,
    WorldcupMap2,
  },
  data: () => ({
    rounds: [],
    round: 0,
    randoms: [],
    randomHouses: [],
    h1: {},
    h2: {},
    cnt: 0,
    key1: 0,
    key2: 0,
  }),
  created() {
    this.makeRound();
  },
  methods: {
    ...mapGetters("house", ["returnHousesNum"]),
    ...mapActions("map", ["getWorldCupAddr"]),
    makeRound() {
      const rounds = [];
      let num = 1;
      if (this.returnHousesNum() === 0) {
        return;
      }
      do {
        if (num !== 1) {
          rounds.push(num);
        }
        num *= 2;
      } while (num <= this.returnHousesNum());
      this.rounds = rounds;
    },
    readyGame() {
      alert(this.round);
      this.randomHouses = [];
      this.randoms = this.makeRandomArray();
      this.randoms.forEach((element) => {
        this.randomHouses.push(HOUSE.state.houses[element]);
      });
      console.log(this.randomHouses);
      this.round /= 2;
      this.worldCup();
    },
    worldCup() {
      this.h1 = this.randomHouses.shift();
      this.h2 = this.randomHouses.shift();
      this.getWorldCupAddr({
        addr1: { dong: this.h1.법정동, jibun: this.h1.지번 },
        addr2: { dong: this.h2.법정동, jibun: this.h2.지번 },
      });
      this.forceRender();
      console.log(this.h1);
      console.log(this.h2);
    },
    forceRender() {
      this.key1 += 1;
      this.key2 += 1;
    },
    click1() {
      this.cnt++;
      if (this.cnt === this.round) {
        this.round /= 2;
        this.cnt = 0;
        if (this.round < 1) {
          return;
        }
      }
      this.randomHouses.push(this.h1);
      this.worldCup();
    },
    click2() {
      this.cnt++;
      if (this.cnt === this.round) {
        this.round /= 2;
        this.cnt = 0;
        if (this.round < 1) {
          this.h1 = this.h2;
          return;
        }
      }
      this.randomHouses.push(this.h2);
      this.worldCup();
    },
    makeRandomArray() {
      const arr = [];
      let i = 0;
      while (i < this.round) {
        const n = Math.floor(Math.random() * this.returnHousesNum());
        if (!sameNum(n)) {
          arr.push(n);
          i++;
        }
      }
      function sameNum(n) {
        for (var i = 0; i < arr.length; i++) {
          if (n === arr[i]) {
            return true;
          }
        }
        return false;
      }
      return arr;
    },
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
