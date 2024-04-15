<template>
    <el-menu
        active-text-color="#F56C6C"
        background-color="rgb(92,100,114)"
        text-color="#fff"
        :default-active="$route.path"
        :router="true"
    >
        <el-menu-item
            v-for="(item,index) in tabList"
            :index="item.path"
            :key="index"
            class="aside_ch"
        >
            <template #title>{{ item.meta.title }}</template>
        </el-menu-item>
    </el-menu>
</template>

<script>
import router from '@/router'
import { defineComponent } from 'vue';
export default defineComponent({
    name: "ManageMenuComponent",
    setup(){
        let tabList;
        for (let routesKey in router.options.routes) {
            if (router.options.routes[routesKey].name=='manage'){
                tabList = router.options.routes[routesKey].children;
            }
        }
        return{
            tabList,
        }
    }
})
</script>

<style scoped>
.aside_ch{
    color: white;
    border-bottom: lightskyblue 1px dashed;
    background: rgb(92,100,114);
}
</style>