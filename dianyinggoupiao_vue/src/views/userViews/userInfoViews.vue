<template>
    <div style="margin-left: 20%">
        <div style="font-size: 36px;font-family: '新宋体';margin: 40px 0 20px 40px;">
            个人信息
        </div>
        <div>
            <div style="margin-bottom: 20px">
                <p style="font-size: 20px">头像</p>
                <span style="margin-right: 40px">请选择图片文件</span>
                <WarningFilled style="width:1em;height:1em;" />
                <span style="opacity: 0.5">最佳尺寸100*100,仅支持png和jpeg或者jpg格式</span>
            </div>
<!--            上传头像-->
            <el-upload
                class="avatar-uploader"
                action="http://localhost:8181/Images/uploadImage"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload(userAccName,data.type)"
                :http-request="handleImageUpload"
            >
                <div class="demo-image__error">
                    <div class="block">
                        <el-image v-if="imageUrl.value!==''" :src="imageUrl">
                            <template #error>
                                <div class="image-slot">
                                    <el-icon><Picture/></el-icon>
                                </div>
                            </template>
                        </el-image>
                    </div>
                </div>
            </el-upload>
        </div>
        <el-form class="el-form--default" :model="form" label-width="auto" style="max-width: 600px;margin-top: 30px;">
            <el-form-item label="账号：">
                <span>{{form.name}}</span>
            </el-form-item>
            <el-form-item label="昵称：">
                <el-input v-model="form.userName" style="width: 40%"/>
            </el-form-item>
            <el-form-item label="性别：">
                <!--label 作为 value 来使用已经被 废弃, 建议label 只用来表示展示的文字，
                    这个被废弃的用法将会在 3.0.0 版本被移除，请考虑使用新 API 替换。
                    新 API value 在2.6.0 中已经可用，您可以使用 value API 来设置复选框的值。
                -->
                <el-radio-group v-model="form.sex">
                    <el-radio value="1" label="1">男</el-radio>
                    <el-radio value="2" label="2">女</el-radio>
                    <el-radio value="3" label="3">保密</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="年龄：">
                <span>{{form.age}}</span>
            </el-form-item>
            <el-form-item label="生日：">
                <el-date-picker
                    v-model="form.birthday"
                    type="date"
                    placeholder="你的出生年月"
                    size="default"
                ></el-date-picker>
            </el-form-item>
            <el-form-item label="地区：">
                <el-select
                    style="margin-right: 20px;"
                    v-model="province"
                    @change="handleChange"
                >
                    <el-option
                        v-for="item in options"
                        :key="item.name"
                        :label="item.name"
                        :value="item.name"
                    />
                </el-select>
                <el-select
                    v-model="city"
                >
                    <el-option
                        v-for="item in cityOptions"
                        :key="item"
                        :label="item"
                        :value="item"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="手机号：">
                <span v-show="!disabled.phoneShow">{{form.phone}}</span>
                <el-input
                    v-show="disabled.phoneShow"
                    v-model="form.phone"
                    style="width: 40%;"
                    :placeholder="form.phone"
                >
                    {{form.phone}}
                </el-input>
                <el-button
                    type="primary"
                    size="small"
                    text
                    @click=" disabled.phoneShow=!disabled.phoneShow"
                >
                    修改
                </el-button>
            </el-form-item>
            <el-form-item label="邮箱：">
                <span v-show="!disabled.mailShow">{{form.mail}}</span>
                <el-input
                    v-show="disabled.mailShow"
                    v-model="form.mail"
                    style="width: 40%;"
                    :placeholder="form.mail"
                >
                    {{form.mail}}
                </el-input>
                <el-button
                    type="primary"
                    size="small"
                    text
                    @click="disabled.mailShow=!disabled.mailShow"
                >
                    修改
                </el-button>

                <el-button type="primary" text @click="save">保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import {computed, reactive, ref} from 'vue'
import {beforeAvatarUpload, handleImageUpload} from '@/util/imageSave'
import {getImageUrl, getUserInfo, postUserInfo, setSelectHttpName} from '@/http/accApi'
import {provincesData} from '@/util/data'
import {ElMessage} from 'element-plus'
export default {
    name: "userInfoViews",
    setup(){
        //-------------------------------变量----------------------------
        const imageUrl = ref('http://localhost:8181/images/avatarImage/userAvatar/avatar.png');
        //reactive用来创建一个响应式对象的
        const data=reactive({
            userAccName:"",
            type:"userAvatar"
        })
        //获取用户账号
        const userAccName=localStorage.getItem('userAccName')
        const form=ref({
            name:"123456789",
            userName:"小明",
            sex:"男",
            age:"20",
            birthday:"2000-1-1",
            area:"",
            phone:"12345678910",
            mail:"1234567890@qq.com",
        });
        const disabled=ref({
            mailShow:false,
            phoneShow:false,
        })
        const options=provincesData
        const province=ref('');
        const cityOptions=ref([]);
        const city=ref('')
        //----------------------------函数--------------------------
        //上传图片处理
        const handleAvatarSuccess = (response, uploadFile) => {
            if (uploadFile && uploadFile.raw) {
                imageUrl.value = URL.createObjectURL(uploadFile.raw);
            } else {
                console.error('uploadFile 或 uploadFile.raw 为 null 或 undefined！');
            }
        };
        data.userAccName=userAccName
        setSelectHttpName("http://localhost:8181/Images/imageIdByUrl")
        getImageUrl(data).then(url=>{
            imageUrl.value=url
        })

        //form表单数据
        const httpName="http://localhost:8181/acc/findUserByName?name="+userAccName
        getUserInfo(httpName).then(respond=>{
            form.value=respond
            if (form.value.area!==null){
                province.value = form.value.area.split("-")[0]
                city.value = form.value.area.split("-")[1]
            }

        })
        //select
        const handleChange=(any)=>{
            //当省份改变时给province赋值
            province.value=any;
            for (const key of options.value) {
                if (key.name===province.value){
                    cityOptions.value=key.cities;
                }
            }
        }
        //save
        const save=()=>{
            form.value.area=province.value+"-"+city.value
            //创建一个计算属性，把Date属性转换成yyyy-mm-dd格式的字符串
            const dateString=computed(()=>{
                const birthday = form.value.birthday;
                if (!birthday) {
                    return '';
                }

                const date = new Date(birthday);
                const year = date.getFullYear();
                const month = String(date.getMonth() + 1).padStart(2, '0');
                const day = String(date.getDate()).padStart(2, '0');

                return `${year}-${month}-${day}`;
            })
            form.value.birthday=dateString.value
            //计算年龄
            if (form.value.birthday) {
                const today = new Date();
                const birthDate = new Date(form.value.birthday);
                let age = today.getFullYear() - birthDate.getFullYear();
                if (today.getMonth() < birthDate.getMonth() || (today.getMonth() === birthDate.getMonth() && today.getDate() < birthDate.getDate())) {
                    age--; // 未过生日则减一岁
                }
                form.value.age = age;
            }
            //保存user
            setSelectHttpName("http://localhost:8181/acc/updateUser")
            postUserInfo(form.value).then(respond=>{
                console.log("respond:")
                console.log(respond)
                if (respond){
                    ElMessage(
                        {
                            message:"保存成功",
                            type:"success"
                        }
                    )
                }else {
                    ElMessage.error("保存失败")
                }
            })
        }
        return{
            imageUrl,
            userAccName,
            form,
            disabled,
            options,
            province,
            cityOptions,
            city,
            data,
            handleAvatarSuccess,
            beforeAvatarUpload,
            handleImageUpload,
            handleChange,
            save,
        }
    }
}

</script>

<style lang="scss" scoped>
.demo-image__error .block {
    display: inline-block;
    box-sizing: border-box;
    vertical-align: top;
    text-align: center;
    width: 100px;
    height: 100px;
    //background: #F5F7FA;
}
.demo-image__error .el-image {
    max-width: 100px;
    max-height: 100px;
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
    left: 0;
}
.demo-image__error .image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background: var(--el-fill-color-light);
    color: var(--el-text-color-secondary);
    font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
    font-size: 30px;
}
.infoPerson >p{
    margin-top: 30px;
}
</style>