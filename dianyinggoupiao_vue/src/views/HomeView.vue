<template xmlns="http://www.w3.org/1999/html">
    <div class="common-layout">
        <el-container>
            <el-header class="header">
                <div class="row">
                    <el-row >
                        <el-col :span="5">
                            <img src="../assets/login.png" style="width: 240px;height:70px">
                        </el-col>
                        <el-col :span="9"><div class="grid-content ep-bg-purple-light" />
                            <el-menu
                                :default-active="$route.path"
                                class="el-menu-demo"
                                mode="horizontal"
                                background-color="#545c64"
                                text-color="#fff"
                                active-text-color="#ffd04b"
                                @select="handleSelect"
                                :router="true"
                            >
                                <el-menu-item index="/Index">首页</el-menu-item>
                                <el-menu-item index="/Movie">电影</el-menu-item>
                                <el-menu-item index="/Cinema">影院</el-menu-item>
                                <el-menu-item index="/HotScreening">榜单</el-menu-item>
                                <el-menu-item index="/Order">订单</el-menu-item>
                            </el-menu>
                        </el-col>
                        <el-col :span="10"><div class="grid-content ep-bg-purple" />
                            <div class="demo-input-size">

                                <el-input
                                    v-model="table.inputName"
                                    class="input"
                                    size="large"
                                    placeholder="请输入电影名称"
                                >
                                    <template #append>
                                        <el-button icon="el-icon-search" @click="searchFun"></el-button>
                                    </template>
                                </el-input>
                                <div class="avatar">
                                    <el-dropdown>

                                        <span class="el-dropdown-link">
                                            <el-avatar :size="45" fit="cover" >
                                                <el-image :src="imageUrl" v-if="imageUrl!==''">
                                                    <template #error>
                                                        <el-image
                                                            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                                                        />
                                                    </template>
                                                </el-image>
                                            </el-avatar>
                                        </span>
                                        <template #dropdown>
                                            <el-dropdown-menu style="border: none;background-color: transparent;box-shadow: none;">
                                                <el-dropdown-item @click="router.push({path:'/UserInfo'})">个人信息</el-dropdown-item>
                                                <el-dropdown-item @click="router.push({path:'/'})">退出登录</el-dropdown-item>
                                            </el-dropdown-menu>
                                        </template>
                                    </el-dropdown>

                                </div>
                                <div class="none"></div>
                            </div>
                        </el-col>
                    </el-row>
                </div>

            </el-header>

            <el-container>
                <el-main class="main"><router-view/></el-main>
            </el-container>
            <el-footer class="el-footer" style="padding: 0;margin: 0;">
                <el-divider/>
                <div class="Footer">
                    <p>联系我们:xxxxxxxxxxx</p>
                    <p>地址:xxxxxxxxxxxxxxxxxx</p>
                </div>
            </el-footer>
        </el-container>
    </div>
</template>

<script lang="js">
import {reactive, ref} from 'vue'
import {Search} from '@element-plus/icons'
import router from '@/router'
import {getImageUrl, setSelectHttpName} from '@/http/cinemaApi'
export default {
    name: 'HomeView',
    components: {Search},
    setup(){
        let table=reactive({
            inputName:"",
        })
        let handleSelect=(key,keyPath,item,routeResult)=>{
            console.log("key:"+key)
            console.log("keyPath:")
            console.log(keyPath)
            console.log("item:")
            console.log(item)
            console.log("routeResult:")
            console.log(routeResult)
        }

        let avatarClick=()=>{}
        let searchFun=()=>{
            router.push({name:"search",query: { searchData: table.inputName }})
        }
        const imageUrl = ref('');
        const data=reactive({
            userAccName:"",
            type:"userAvatar"
        })
        const userAccName=localStorage.getItem('userAccName')
        data.userAccName=userAccName;
        console.log()
        //获取图片url
        setSelectHttpName("http://localhost:8181/Images/imageIdByUrl")
        getImageUrl(data).then(url=>{
            imageUrl.value=url
        })
        return{
            table,
            imageUrl,
            handleSelect,
            avatarClick,
            router,
            searchFun,
        }
    }
}
</script>

<style lang="scss" scoped>
*{
    margin: 0;
    padding: 0;
}
a{
    text-decoration: none; /* 去除默认的下划线 */
    outline: none;	/* 去除旧版浏览器的点击后的外虚线框 */
    color: #000;	/* 去除默认的颜色和点击后变化的颜色 */
}
ul li{
    list-style: none;
}
.common-layout{
    width: 100%;
}

.header{
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
}
.row{
    width: 100%;
}
.main{
    width: 80%;
    margin: 0;
    padding: 0;
}
.aside{
    width: 20%;
}
.el-row{
    background: #545c64;
}
.el-menu{
    height: 100%;
    .el-menu-item{
        height: 100%;
        font-size: 1.5em;
        text-align: center;
    }
}
.input{
    width: 60%;
    text-align: center;
    margin-top: 2%;
    float: left;
}
.avatar{
    width: 30%;
    margin-top: 2%;
    float: right;
    .el-avatar{
        float: right;
        margin-right: 20%;
    }
}
.icon{
    margin-top: 2%;
    width: 6%;
    height: 20%;
    >a{
        color: #9299a1;
        width: 80%;
        height: 80%;
    }
    >a:hover{
        color: #efefef;
        width: 100%;
        height: 100%;
    }
}
.Main_box{
    width: 100%;
    margin: 0;
    padding: 0;
    background: white;
    display: flex;
    flex-direction: column;
    justify-content:space-between;
}
.Footer {
    width: 100%;
    height: 120px;
    text-align: center;
    background: #9299a1;
    margin:0 auto;
    padding-top: 60px;
}
</style>
