<template>
    <el-container class="common-layout">
        <el-header class="Header">
            <el-row>
                <el-col :span="8">
                    <div class="avatar">
                        <el-image :src="imageUrl" v-if="imageUrl!==''">
                            <template #error>
                                <el-image
                                    src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                                />
                            </template>
                        </el-image>
                    </div>
                </el-col>
                <el-col :span="8">
                    <div style="font-size: 28px;text-align: center;color: snow">影院管理</div>
                </el-col>
                <el-col :span="8">
                    <div class="back">
                        <el-button type="primary" @click="backFun">注销</el-button>
                    </div>
                </el-col>
            </el-row>
        </el-header>
        <el-container style="background: rgb(92,100,114);">
            <div class="Aside">
                <el-aside width="200px">
                    <MenuComponent/>
                </el-aside>
            </div>
            <el-container>
                <div class="Main_box">
                    <div class="Main">
                        <el-main>
                            <router-view :cinemaAccName="cinemaAccName"/>
                        </el-main>
                    </div>
                    <el-footer style="background: white;padding: 0;margin: 0;">
                        <el-divider/>
                        <div class="Footer">
                            <p>联系我们:xxxxxxxxxxx</p>
                            <p>地址:xxxxxxxxxxxxxxxxxx</p>
                        </div>
                    </el-footer>
                </div>
            </el-container>
        </el-container>
    </el-container>
</template>

<script>
import router from '@/router'
import MenuComponent from './CinemaMenuComponent.vue'
import {reactive, ref} from 'vue'
import {getImageUrl, setSelectHttpName} from '@/http/cinemaApi'
export default {
    name: "cinemaManageViews",
    components: {
        MenuComponent
    },
    setup() {
        let backFun = () => {
            router.push({path: "/"})
        }

        const imageUrl = ref('');
        const data=reactive({
            userAccName:"",
            type:"cinemaAvatar"
        })
        const cinemaAccName=localStorage.getItem('cinemaAccName')
        data.userAccName=cinemaAccName;
        //获取图片url
        setSelectHttpName("http://localhost:8181/Images/imageIdByUrl")
        getImageUrl(data).then(url=>{
            imageUrl.value=url
        })
        return {
            imageUrl,
            backFun,
            cinemaAccName,
        }
    }
}

</script>

<style lang="scss" scoped>
html,body {
    height: 100%;
    width: 100%;
    margin: 0;
    padding: 0;
}
.common-layout {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    //overflow: hidden;
    //z-index: -1;
    background: rgb(92,100,114);
    overflow: auto;
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
    bottom: 0;
    text-align: center;
    background: #9299a1;
    margin:0 auto;
    padding-top: 60px;
}
.Header{
    padding: 10px;
    margin:20px;
}
.Header img {
    width: 60px;
    height: 60px;
}

.back {
    text-align: right;
}
.Aside{
    margin-top: 29px;
}
.aside_ch{
    color: white;
    border-bottom: lightskyblue 1px dashed;
    background: rgb(92,100,114);
}
.avatar{
    border: white 0.5px solid;
    width: 60px;        /* 设置元素的宽度 */
    height: 60px;       /* 设置元素的高度 */
    border-radius: 50%;  /* 将边框半径设置为元素宽度或高度的一半，实现圆形效果 */
    overflow: hidden;    /* 隐藏元素内容超出部分，确保仅显示圆形内容 */
}

</style>