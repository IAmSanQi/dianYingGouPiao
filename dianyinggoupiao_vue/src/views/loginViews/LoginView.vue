<template>
    <div class="one">
        <div class="login" >
        <h1>电影购票系统实现与设计</h1>
        <el-form
            label-width="100px"
            :model="loginData"
            style="max-width: 460px"
        >
            <el-form-item label="用户:">
                <el-input placeholder="请输入账号" v-model="loginData.name"/>
            </el-form-item>
            <el-form-item label="密码:">
                <el-input
                    v-model="loginData.password"
                    type="password"
                    placeholder="请输入密码"
                    show-password
                />
            </el-form-item>
            <el-form-item>
                <el-button @click="subFun" type="primary">登录</el-button>
                <el-button type="primary" @click="regFun" >注册</el-button>
            </el-form-item>
            <div style="margin-top: 40px">
                <el-button type="primary" class="leftButton" @click="cinemaFun"><span>影院登录</span></el-button>
                <el-button type="primary" class="rightButton" @click="manageFun"><span>后台登录</span></el-button>
            </div>

        </el-form>

    </div>
    </div>
</template>

<script>

import {inject, reactive} from "vue"
import {ElMessage} from 'element-plus'
import {loginHttp, setSelectHttpName} from '@/http/accApi'
import router from "../../router"

export default {
    name: 'LoginView',
    setup() {
        let loginData = reactive({
            id: null,
            name: "",
            password: "",
            type: ""
        })
        //inject获取父组件中用provide提供的数据
        // const globalVariable = inject('globalVariable');
        setSelectHttpName("http://localhost:8181/acc/findUserAccAll")
        let subFun = () => {
            if (loginData.name == null || loginData.name.length === 0) {
                ElMessage.error("账号不能为空");
                return 2;
            } else if (loginData.password == null || loginData.password.length === 0) {
                ElMessage.error("密码不能为空");
                return 2;
            }
            loginHttp(loginData).then(res => {
                for (const resKey in res) {
                    if (loginData.name === res[resKey].name && loginData.password === res[resKey].password){
                        router.push({path: 'Index'});
                        // 存储值到 localStorage
                        localStorage.setItem('userAccName', loginData.name);
                        return 1;
                        // if (res[resKey].type==="0"){
                        //     router.push({path: 'MemberRecord'});
                        //     return 0;
                        // }else if(res[resKey].type==="1"){
                        //     router.push({path: 'Index'});
                        //     // 存储值到 localStorage
                        //     localStorage.setItem('globalVariable', loginData.name);
                        //     return 1;
                        // }else {
                        //     router.push({path: 'CinemaMangeViews'});
                        //     // 存储值到 localStorage
                        //     localStorage.setItem('globalVariable', loginData.name);
                        //     return 2;
                        // }
                    }
                }
                ElMessage.error("账号或密码错误");
            })
        }
        let cinemaFun=()=>{
            router.push({name: 'cinemaLogin'});
        }
        let manageFun=()=>{
            router.push({name: 'manageLogin'});
        }
        let regFun=()=>{
            router.push({path:'Register'});
        }

        return {
            loginData,
            subFun,
            regFun,
            cinemaFun,
            manageFun
        }
    }
}
</script>
<style lang="scss" scoped>
.one{
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: 0;
    padding: 0;
    background-image: url('../../assets/背景.png');
    background-size: cover;
}
.login {
    width: 500px;
    border: 0.2px lightblue dashed;
    float: right;
    margin-top: 180px;
    margin-right: 40px;
    border-radius: 10px;
    padding: 20px;
    text-align: center;
    position: relative;
    opacity: 0.8;
    color: lightblue;
}
.leftButton{
    position: absolute;
    left: 0;
    bottom: 5px;
    margin-left: 5px;
    text-align: center;
    font-size: 12px;
    width: 120px;
}
.leftButton span{
    position: relative;
    transition: 0.8s;
}
.leftButton span:before{
    content: '<<';
    position: absolute;
    opacity: 0;
    transition: 0.8s;
}
.leftButton:hover span{
    padding-left: 25px;
}
.leftButton:hover span:before {
    opacity: 1;
    left: 0;
}

.rightButton{
    position: absolute;
    right: 0;
    bottom: 5px;
    margin-right: 5px;
    font-size: 12px;
    width: 120px;
}

.rightButton span{
    position: relative;
    transition: 0.8s;
}
.rightButton span:after{
    content: '>>';
    position: absolute;
    opacity: 0;
    transition: 0.8s;
}
.rightButton:hover span{
    padding-right: 25px;
}
.rightButton:hover span:after {
    opacity: 1;
    right: 0;
}
:deep(.el-form-item__label) {
    color: white;
}
</style>
