<template>
    <div class="one">
        <el-button link  icon="el-icon-arrow-left"  >
           <router-link to="/" style="color: white">
               <el-icon><ArrowLeftBold /></el-icon>返回
           </router-link>
        </el-button>
        <div class="cinemaLogin">
            <h1>影院后台登录</h1>
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
    name: "cinemaLogin",
    setup() {
        let loginData = reactive({
            id: null,
            name: "",
            password: "",
            type: ""
        })
        //inject获取父组件中用provide提供的数据
        const globalVariable = inject('globalVariable');
        setSelectHttpName("http://localhost:8181/acc/findCinemaAccAll")
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
                    if (loginData.name === res[resKey].name && loginData.password === res[resKey].password) {
                        router.push({path: 'cinemaPersonInfo'});
                        // 存储值到 localStorage
                        localStorage.setItem('cinemaAccName', loginData.name);
                        return 2;
                    }
                }
                ElMessage.error("账号或密码错误");
            })
        }
        let regFun=()=>{
             router.push({path:'CinemaRegister'});
        }

        return {
            loginData,
            subFun,
            regFun
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
    background-image: url('../../assets/影院背景.png');
    background-size: cover;
}
.cinemaLogin {
    width: 500px;
    margin-top: 150px;
    float: right;
    border: 1px #efefef solid;
    border-radius: 10px;
    padding: 20px;
    text-align: center;
    color: snow;
}
:deep(.el-form-item__label)  {
    color: white;
}
</style>