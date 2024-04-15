import { createStore } from 'vuex';

const store = createStore({
  state: {
    countdown: 15 * 60
  },
  mutations: {
    decrementCountdown(state) {
      if (state.countdown > 0) {
        state.countdown--;
      }
    }
  },
  actions: {
    updateCountdown({ commit }) {
      const intervalId = setInterval(() => {
        commit('decrementCountdown');
      }, 1000);

      return intervalId; // 返回 intervalId，以便在组件中清除定时器
    },
    clearCountdownInterval(_, intervalId) {
      clearInterval(intervalId);
    }
  },
  getters: {
    minute: state => Math.floor(state.countdown / 60),
    second: state => state.countdown % 60
  }
});

export default store;

