<template>
    <div>
        <el-menu :default-active="$route.path"
                 class="el-menu-demo TopMenu"
                 mode="horizontal"
                 @select="handleSelect"
                 :router="true"
        >
            <el-menu-item index="/HotScreening">热映口碑榜</el-menu-item>
            <el-menu-item index="/Expect">最受期待榜</el-menu-item>
            <el-menu-item index="/Picket">国内票房榜</el-menu-item>
            <el-menu-item index="/Ranking">Top100榜</el-menu-item>
        </el-menu>
        <div>
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
export default {
    name: "listViews",
    setup(){
        let handleSelect=()=>{
        }
        return{
            handleSelect,
        }
    }
}
</script>

<style scoped lang="scss">
.TopMenu{
    text-align: center;
    margin-left: 35%;
}

</style>