<template>
    <div style="margin-left: 40px">
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
                :before-upload="beforeAvatarUpload(adminAccName,data.type)"
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
        <!--        表单-->
        <el-form class="el-form--default"
                 :model="form"
                 label-width="auto"
                 label-position="left"
                 style="max-width: 600px;margin-top: 30px;">
            <el-form-item label="账号：">
                <span>{{adminAccName}}</span>
            </el-form-item>
        </el-form>
    </div>
</template>

<script lang="js">


import {reactive, ref} from 'vue'
import {beforeAvatarUpload, handleImageUpload} from '@/util/imageSave'
import {getImageUrl, setSelectHttpName} from '@/http/cinemaApi'

export default {
    name: "adminPersonInfo",
    setup() {
        const imageUrl = ref('');
        const data=reactive({
            userAccName:"",
            type:"manageAvatar"
        })
        const form=ref({
            name:"万象影城（吴江万象汇店）",
        });
        //获取用户账号
        const adminAccName=localStorage.getItem('manageAccName')
        console.log(adminAccName)
        data.userAccName=adminAccName;

        //---------------------------函数---------------------------
        //获取图片url
        setSelectHttpName("http://localhost:8181/Images/imageIdByUrl")
        getImageUrl(data).then(url=>{
            imageUrl.value=url
        })
        //上传成功
        const handleAvatarSuccess = (response, uploadFile) => {
            if (uploadFile && uploadFile.raw) {
                imageUrl.value = URL.createObjectURL(uploadFile.raw);
            } else {
                console.error('uploadFile 或 uploadFile.raw 为 null 或 undefined！');
            }
        };

        return {
            imageUrl,
            data,
            form,
            adminAccName,
            handleImageUpload,
            beforeAvatarUpload,
            handleAvatarSuccess,
        }
    },
}
</script>

<style scoped lang="scss">
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