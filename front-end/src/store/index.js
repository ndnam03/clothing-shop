// store/index.js
import { createStore } from "vuex";

const store = createStore({
  state: {
    isAuthenticated: false,
    token: null,
  },
  mutations: {
    setToken(state, token) {
      state.token = token;
      state.isAuthenticated = true;
    },
    logout(state) {
      state.token = null;
      state.isAuthenticated = false;
    },
  },
});

export default store;
