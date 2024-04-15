import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';
const storeOrder = createStore({
    plugins: [createPersistedState()], // 添加 createPersistedState 插件
    state: {
        orders: [],
        timers: {}, // 存储订单 ID 到定时器的映射关系
    },
    mutations: {
        //添加一个订单数据
        addOrder(state, order) {
            state.orders.push(order);
        },
        //移除一个订单数据
        removeOrder(state, orderId) {
            state.orders = state.orders.filter(order => order.order_id !== orderId);
            // 移除订单相关的定时器
            clearInterval(state.timers[orderId]);
            delete state.timers[orderId];
        },
        //根据订单号开始倒计时，通常会在启动倒计时的定时器中被调用
        decrementCountdown(state, orderId) {
            const order = state.orders.find(order => order.order_id === orderId);
            if (order && order.countdown > 0) {
                order.countdown--;
            }
        },
        // 添加订单的定时器
        addTimer(state, { orderId, intervalId }) {
            state.timers[orderId] = intervalId;
        },
    },
    actions: {
        //启动订单的倒计时，返回一个定时器的 intervalId
        startCountdown({ commit, state }, orderId) {
            // 先清除之前的定时器
            if (state.timers[orderId]) {
                clearInterval(state.timers[orderId]);
            }
            // 启动新的倒计时
            const intervalId = setInterval(() => {
                commit('decrementCountdown', orderId);
            }, 1000);

            // 将 intervalId 存储到 state 中
            commit('addTimer', { orderId, intervalId });

            return intervalId; // 返回 intervalId，以便在组件中清除定时器
        },
        //停止订单的倒计时，从 orders 数组中删除指定的订单。
        stopCountdown({ commit }, orderId) {
            commit('removeOrder', orderId);
        },
    },
    getters: {
        //根据订单号获取订单对象
        getOrderById: state => orderId => state.orders.find(order => order.order_id === orderId),
        //根据订单号格式化显示倒计时为分钟和秒
        getFormattedCountdown: (state) => (orderId) => {
            const order = state.orders.find(order => order.order_id === orderId);
            if (!order) return '';
            const minutes = Math.floor(order.countdown / 60);
            const seconds = order.countdown % 60;
            return `${minutes} 分钟 ${seconds} 秒`;
        },
        //获取订单的分钟
        minute: (state, getters) => orderId => {
            const order = getters.getOrderById(orderId);
            if (!order) return 0;
            return Math.floor(order.countdown / 60);
        },
        //获取订单的秒
        second: (state, getters) => orderId => {
            const order = getters.getOrderById(orderId);
            if (!order) return 0;
            return order.countdown % 60;
        }
    },
});

export default storeOrder;