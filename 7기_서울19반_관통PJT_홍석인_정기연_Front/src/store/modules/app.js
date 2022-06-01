// Pathify
import { make } from "vuex-pathify";

// Data
const state = {
  drawer: null,
  drawerImage: true,
  mini: false,
  items: [
    {
      title: "Dashboard",
      icon: "mdi-view-dashboard",
      to: "/",
    },
    {
      title: "House List",
      icon: "mdi-clipboard-outline",
      to: "/house/",
    },
    {
      title: "User Profile",
      icon: "mdi-account",
      to: "/components/profile/",
    },
    {
      title: "House WorldCup",
      icon: "mdi-chart-bubble",
      to: "/worldcup/",
    },
    {
      title: "Map",
      icon: "mdi-map-marker",
      to: "/maps",
    },
  ],
};

const mutations = make.mutations(state);

const actions = {
  ...make.actions(state),
  init: async ({ dispatch }) => {
    //
  },
};

const getters = {};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
