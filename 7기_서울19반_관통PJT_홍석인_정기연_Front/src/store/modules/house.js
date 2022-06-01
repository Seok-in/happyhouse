// Pathify
import http from "@/api/http";
const state = {
  sidos: [{ value: null, text: "선택하세요" }],
  guguns: [{ value: null, text: "선택하세요" }],
  houses: [],
  house: null,
  housesNum: 0,
  nowSidoCode: "",
  nowGugunCode: "",
  sidoStr: "",
  gugunStr: "",
  todos: [
    // { title: '할 일1', completed: false },
    // { title: '할 일2', completed: false },
  ],
};

const getters = {
  returnHousesNum(state) {
    return state.housesNum;
  },
  returnSido(state) {
    return state.sidoStr;
  },
  returnGugun(state) {
    return state.gugunStr;
  },
  allTodosCount(state) {
    return state.todos.length;
  },
  completedTodosCount(state) {
    return state.todos.filter((todo) => {
      return todo.completed === true;
    }).length;
  },
  unCompletedTodosCount(state) {
    return state.todos.filter((todo) => {
      return todo.completed === false;
    }).length;
  },
};

const mutations = {
  SET_SIDO_LIST(state, sidos) {
    sidos.forEach((sido) => {
      state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
    });
  },
  SET_GUGUN_LIST(state, guguns) {
    guguns.forEach((gugun) => {
      state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
    });
  },
  CLEAR_SIDO_LIST(state) {
    state.sidos = [{ value: null, text: "선택하세요" }];
  },
  CLEAR_GUGUN_LIST(state) {
    state.guguns = [{ value: null, text: "선택하세요" }];
  },
  SET_HOUSE_LIST(state, houses) {
    state.houses = houses;
    state.housesNum = houses.length;
    console.log(state.houses);
  },
  SET_DETAIL_HOUSE(state, house) {
    console.log("Mutations", house);
    state.house = house;
  },
};

const actions = {
  getSido({ commit }) {
    http
      .get(`/map/sido`)
      .then(({ data }) => {
        // console.log(data);
        commit("SET_SIDO_LIST", data);
      })
      .catch((error) => {
        console.log(error);
      });
  },
  getGugun({ commit }, sidoCode) {
    const params = { sido: sidoCode };
    state.nowSidoCode = sidoCode;
    function compareVal(temp) {
      console.log(temp);
      console.log(state.nowSidoCode);
      return temp.value === state.nowSidoCode;
    }
    state.sidoStr = state.sidos.find(compareVal).text;
    console.log(state.sidoStr);
    http
      .get(`/map/gugun`, { params })
      .then(({ data }) => {
        commit("SET_GUGUN_LIST", data);
      })
      .catch((error) => {
        console.log(error);
      });
  },
  getHouseList({ commit }, gugunCode) {
    // const SERVICE_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;
    state.nowGugunCode = gugunCode;
    function compareVal(temp) {
      console.log(temp);
      console.log(state.nowGugunCode);
      return temp.value === state.nowGugunCode;
    }
    state.gugunStr = state.guguns.find(compareVal).text;
    console.log(state.gugunStr);
    const SERVICE_KEY =
      "1ZjNX1GaqAKRSWyRxo736Gubq7KIf4ObPd9oqGeJZt7GrEFnDFheRLaqMszYbSdQ20Fvlo5Y9C6xdkZ31FiQcQ%3D%3D";
    const SERVICE_URL =
      "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
    const params = {
      LAWD_CD: gugunCode,
      DEAL_YMD: "202203",
      serviceKey: decodeURIComponent(SERVICE_KEY),
    };
    http
      .post("/house", null, {
        params: {
          LAWD_CD: gugunCode,
          DEAL_YMD: "202203",
          serviceKey: decodeURIComponent(SERVICE_KEY),
        },
      })
      .then(({ data }) => {
        console.log(data);
        commit("SET_HOUSE_LIST", data);
      });
    // http
    //   .get(SERVICE_URL, {
    //     params,
    //   })
    //   .then(({ data }) => {
    //     // console.log(commit, data);
    //     commit("SET_HOUSE_LIST", data.response.body.items.item);
    //   })
    //   .catch((error) => {
    //     console.log(error);
    //   });
  },
  detailHouse({ commit }, house) {
    // 나중에 house.일련번호를 이용하여 API 호출
    // console.log(commit, house);
    commit("SET_DETAIL_HOUSE", house);
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
