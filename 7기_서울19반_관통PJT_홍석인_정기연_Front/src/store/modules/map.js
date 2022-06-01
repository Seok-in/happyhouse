// Pathify
import house from "./house";
const state = {
  dong: "",

  jibun: "",

  address: "",
  addr1: "",
  addr2: "",
};

const getters = {
  getAddress(state) {
    if (
      state.jibun != null &&
      state.jibun !== "" &&
      state.dong != null &&
      state.dong !== ""
    ) {
      return (
        house.state.sidoStr +
        " " +
        house.state.gugunStr +
        " " +
        state.dong +
        " " +
        state.jibun
      );
    }
  },
  getterAddr(state) {
    return state.address;
  },
  getterAddr1(state) {
    return state.addr1;
  },
  getterAddr2(state) {
    return state.addr2;
  },
};

const mutations = {
  SET_ADDRESS_JIBUN(state, str) {
    state.jibun = str;
  },
  SET_ADDRESS_DONG(state, str) {
    state.dong = str;
  },
  SET_ADDRESS(state, str) {
    state.address = str;
  },
  SET_ADDR(state, data) {
    state.addr1 =
      house.state.sidoStr +
      " " +
      house.state.gugunStr +
      " " +
      data.addr1.dong +
      " " +
      data.addr1.jibun;
    state.addr2 =
      house.state.sidoStr +
      " " +
      house.state.gugunStr +
      " " +
      data.addr2.dong +
      " " +
      data.addr2.jibun;
  },
};

const actions = {
  getStr({ commit }, data) {
    // 나중에 house.일련번호를 이용하여 API 호출
    // console.log(commit, house);
    state.jibun = "";
    state.dong = "";
    commit("SET_ADDRESS_DONG", data.dong);
    commit("SET_ADDRESS_JIBUN", data.jibun);
  },
  getAddr({ commit }, payload) {
    commit("SET_ADDRESS", payload);
  },
  getWorldCupAddr({ commit }, payload) {
    commit("SET_ADDR", payload);
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
