<template>
    <div @click="back" class="back"><el-icon><ArrowLeftBold /></el-icon>返回</div>
    <div class="reg-box">
        <el-form
            ref="userInfoFormRef"
            label-width="100px"
            :model="Account"
            style="max-width: 460px"
            :rules="rules"
            class="form"
        >
            <el-form-item label="账号" prop="name">
                <el-input v-model="Account.name" placeholder="123"/>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="Account.password" placeholder="123456"/>
            </el-form-item>
            <el-form-item label="姓名" prop="user.name">
                <el-input v-model="Account.user.userName" placeholder="张三"/>
            </el-form-item>
            <el-form-item label="年龄" prop="user.age">
                <el-input v-model="Account.user.age" placeholder="20"/>
            </el-form-item>
            <el-form-item label="性别" prop="user.sex">
                <div>
                    <el-radio-group v-model="Account.user.sex" class="ml-4">
                        <el-radio label="1" size="large">男</el-radio>
                        <el-radio label="0" size="large">女</el-radio>
                    </el-radio-group>
                </div>
            </el-form-item>
            <el-form-item label="手机号" prop="user.phone">
                <el-input v-model="Account.user.phone" placeholder="15264523547"/>
            </el-form-item>
            <el-form-item label="邮箱" prop="user.mail">
                <el-input v-model="Account.user.mail" placeholder="3423782892@qq.com"/>
            </el-form-item>
            <el-form-item >
                <el-button @click="submitForm" type="primary">提交</el-button>
                <el-button @click="resetForm" type="primary">清空</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script lang="js">
import {reactive, getCurrentInstance} from 'vue'
import router from "../router"
import $axios from 'axios'
import { ElMessage } from 'element-plus'

export default {

    name: "RegisterView",
    setup() {
        const Account = reactive({
            id: null,
            name: "",
            password: "",
            type: "2",
            user: {
                name: "",
                userName: "",
                age: "",
                sex: "",
                phone: "",
                mail: "",
            },
        })
        const rules = reactive({
            name: [
                {required: true, message: '请输入你的账号', trigger: 'blur'},
                {min: 3, max: 5, message: '最少3个最多5个', trigger: 'blur'},
            ],
            password: [
                {required: true, message: '请输入你的密码', trigger: 'blur'},
                {min: 6, max: 16, message: '最少3未最多16位', trigger: 'blur'},
            ],
            user: {
                userName: [
                    {required: true, message: '请输入你的用户名', trigger: 'blur'},
                    {min: 2, max: 5, message: '最少3个最多5个', trigger: 'blur'},
                ],
                age: [
                    {required: true, message: '请输入你的年龄', trigger: 'blur'},
                    // { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
                ],
                sex: [
                    {required: true, message: '选择你的性别', trigger: 'change'},
                    // { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
                ],
                phone: [
                    {required: true, message: '请输入你的手机号', trigger: 'blur'},
                    {min: 11, max: 11, message: '请输入11位的手机号', trigger: 'blur'},
                ],
                mail: [
                    {required: true, message: '请输入你的邮箱', trigger: 'blur'},
                    {min: 3, max: 30, message: '邮箱格式错误', trigger: 'blur'},
                ],
            },
        })
        let that = getCurrentInstance()

        const submitForm = () => {
            that.refs.userInfoFormRef.validate((valid) => {
                if (valid) {
                    $axios.post("http://localhost:8181/acc/addAccAndUserName",
                        Account,
                        {contentType: "application/json"}).then(function (res) {

                        if (res.data==="账号不能为空"){
                            ElMessage.error('账号不能为空')
                        }else if(res.data==="用户已存在"){
                            ElMessage.error('用户已存在')
                        }else{
                            router.push({path: 'Success'})
                        }
                    }).catch(function (error) {
                        console.log(error);
                        ElMessage.error('系统正在维修...')
                    });
                } else {
                    console.log('error submit!')
                }
            })

        }
        let back=()=>{
            router.push({path: '/'})
        }
        const resetForm = () => {
            that.refs.userInfoFormRef.resetFields()
        }
        return {
            Account,
            submitForm,
            rules,
            resetForm,
            back,
        }
    },
}
</script>

<style scoped lang="scss">
.reg-box {
    width: 60%;
    margin: 80px auto;
    >form{
        margin: auto;
    }
}

.form {
    width: 60%;
    margin: 40px auto;
    text-align: center;
    > el-form-item {
        margin: 20px;
    }
}
.back{
    width: 80px;
    font-family: 新宋体;
    left: 20px;
    cursor: pointer;
}
</style>