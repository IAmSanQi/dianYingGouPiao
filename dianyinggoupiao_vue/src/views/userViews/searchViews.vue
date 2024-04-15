<template>
    <div>
        <div class="search_heard">
            <div class="search_heard_input">
                <el-input
                    v-model="searchData.input"
                    placeholder="请输入电影名称"
                >
                    <template #append>
                        <el-button icon="el-icon-search" @click="findMovie"></el-button>
                    </template>
                </el-input>
            </div>
            <p @click="movies"><a href="#" >影视剧(<span>1</span>)</a></p>
            <p @click="cinema"><a href="#" >影院(<span>0</span>)</a></p>
            <div class="san_left"><div ref="san_left"></div></div>
            <div class="san_right"><div ref="san_right"></div></div>
        </div>
        <div>
            <router-view></router-view>
        </div>
    </div>
</template>

<script lang="js">
import { useRoute } from 'vue-router';
import {reactive, ref, watchEffect} from 'vue'
import router from '@/router'

export default {
    name: "searchViews",
    setup(){
        const route=useRoute()
        let searchData=reactive({
            input:""
        })
        searchData.input=route.query.searchData
        watchEffect(()=>{
            router.push({path:"/MovieContent",query:{searchData:searchData.input}})
        })
        let san_left=ref(null)
        let san_right=ref(null)
        let movies=()=>{
            router.push({path:"/MovieContent"})
            san_left.value.style.display="inline"
            san_right.value.style.display="none"
        }
        let cinema=()=>{
            san_right.value.style.display="inline"
            san_left.value.style.display="none"
            router.push({path:"/CinemaContent"})
        }
        let findMovie=()=>{
            router.push({path:"/MovieContent",query:{searchData:searchData.input}})
        }
        return{
            searchData,
            san_left,
            san_right,
            movies,
            cinema,
            findMovie,
        }
    }
}

</script>

<style lang="scss" scoped>
*{margin: 0;padding: 0;}
a{
    text-decoration: none; /* 去除默认的下划线 */
    outline: none;	/* 去除旧版浏览器的点击后的外虚线框 */
    color: #000;	/* 去除默认的颜色和点击后变化的颜色 */
}
.search_heard{
    width: 100%;
    float: left;
    border-bottom: #ffb400 solid 1px;
    p:nth-child(2){
        width: 45%;
        float: left;
        font-size: 1.5rem;
        text-align: right;
        margin-top: 2%;
        color: rgb(82,82,84);
    }
    p:nth-child(2):before{

    }
    p:nth-child(3){
        width: 45%;
        float: right;
        font-size: 1.5rem;
        text-align: left;
        margin-top: 2%;
        color: rgb(82,82,84);
    }
    >p:hover{
        color: #ffb400;
        >a:hover{
            color: #ffb400;
        }
        >a:not(:visited) {
            color: black;
        }
        >a:visited {
            //color: red;
        }
    }
}
.search_heard_input{
    width: 40%;
    float: left;
    margin-left: 30%;
    margin-top: 4%;
    border: black 1px solid;
    border-radius: 50px;
    overflow: hidden;
}
.san_left{
    width:45% ;
    float: left;
    >div{
        width: 2px;
        float: right;
        border-left: 15px solid transparent; /* 左边透明 */
        border-right: 15px solid transparent; /* 右边透明 */
        border-bottom: 20px solid #ffb400; /* 底部有颜色 */
        margin-right: 5%;
        display: none;
    }
}
.san_right{
    width:45% ;
    float: right;
    >div{
        width: 2px;
        float: left;
        border-left: 15px solid transparent; /* 左边透明 */
        border-right: 15px solid transparent; /* 右边透明 */
        border-bottom: 20px solid #ffb400; /* 底部有颜色 */
        margin-left: 5%;
        display: none;
    }
}
</style>