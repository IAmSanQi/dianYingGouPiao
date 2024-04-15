import {createApp, reactive, ref} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import storeOrder from './store'
import installElementPlus from './plugins/element'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'



const app=createApp(App)
const globalVariable = ref('');
const userAccName = ref('');
const cinemaAccName = ref('');
const manageAccName = ref('');
//调用函数 installElementPlus()，并传入了之前创建的 Vue 应用实例 app,全局注册 Element Plus 组件
installElementPlus(app)
/**
 * app.provide('globalVariable', globalVariable)
 *      使用 provide 方法，将之前创建的 globalVariable 变量提供给应用的所有子组件，这样所有的子组件都可以通过依赖注入来访问到这个全局变量。
 * app.use(storeOrder).use(router)
 *      将 Vuex 的 storeOrder 和 Vue Router 注入到应用中，以便在应用中使用状态管理和路由功能。
 * app.mount('#app')
 *      将应用实例挂载到 HTML 页面中具有 id="app" 的 DOM 元素上，这个元素将作为 Vue 应用的根 DOM 元素。
 */
app.provide('globalVariable', globalVariable).provide('userAccName',userAccName)
    .provide('cinemaAccName',cinemaAccName).provide('manageAccName',manageAccName)
    .use(store).use(storeOrder).use(router).mount('#app')
//遍历 ElementPlusIconsVue 对象中的所有属性，然后通过 app.component() 方法将这些属性注册为全局组件，以便在应用的任何地方使用这些图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}