<template>
    <div>
        <div class="top">
            <el-menu
                :default-active="menu_default"
                class="el-menu-demo top_menu"
                mode="horizontal"
                @select="handleSelect"
            >
                <el-menu-item index="1">正在热映</el-menu-item>
                <el-menu-item index="2">即将上映</el-menu-item>
                <el-menu-item index="3">经典影片</el-menu-item>
            </el-menu>
        </div>
        <div class="tags-panel">
            <ul class="tags-lines">
                <li class="tags-line">
                    <div class="tags-title">类型 :</div>
                    <ul class="tags">
                        <li class="active">
                            <a data-val="{TagName:'全部'}" href="#" style="cursor: pointer">全部</a>
                        </li>
                        <li>
                            <a data-val="{TagName:'爱情'}" href="#">爱情</a>
                        </li>
                        <li>
                            <a data-val="{TagName:'喜剧'}" href="#">喜剧</a>
                        </li>
                        <li>
                            <a data-val="{TagName:'动画'}" href="#">动画</a>
                        </li>
                        <li>
                            <a data-val="{TagName:'剧情'}" href="#">剧情</a>
                        </li>
                        <li>
                            <a data-val="{TagName:'恐怖'}" href="#">恐怖</a>
                        </li>
                        <li>
                            <a data-val="{TagName:'惊悚'}" href="#">惊悚</a>
                        </li>
                        <li>
                            <a data-val="{TagName:'科幻'}" href="#">科幻</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'动作'}" href="#">动作</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'悬疑'}" href="#">悬疑</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'犯罪'}" href="#">犯罪</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'冒险'}" href="#">冒险</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'战争'}" href="#">战争</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'奇幻'}" href="#">奇幻</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'运动'}" href="#">运动</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'家庭'}" href="#">家庭</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'古装'}" href="#">古装</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'武侠'}" href="#">武侠</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'西部'}" href="#">西部</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'历史'}" href="#">历史</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'传记'}" href="#">传记</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'歌舞'}" href="#">歌舞</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'黑色电影'}" href="#">黑色电影</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'短片'}" href="#">短片</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'纪录片'}" href="#">纪录片</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'戏曲'}" href="#">戏曲</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'音乐'}" href="#">音乐</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'灾难'}" href="#">灾难</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'青春'}" href="#">青春</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'儿童'}" href="#">儿童</a>
                        </li>
                        <li>
                            <a  data-val="{TagName:'其他'}" href="#">其他</a>
                        </li>
                    </ul>
                </li>
                <li class="tags-line tags-line-border">
                    <div class="tags-title">区域 :</div>
                    <ul class="tags">
                        <li class="active">
                            <a href="#" style="cursor: pointer">全部</a>
                        </li>
                        <li>
                            <a href="#">大陆</a>
                        </li>
                        <li>
                            <a href="#">美国</a>
                        </li>
                        <li>
                            <a href="#">韩国</a>
                        </li>
                        <li>
                            <a href="#">日本</a>
                        </li>
                        <li>
                            <a href="#">中国香港</a>
                        </li>
                        <li>
                            <a href="#">中国台湾</a>
                        </li>
                        <li>
                            <a href="#">泰国</a>
                        </li>
                        <li>
                            <a href="#">印度</a>
                        </li>
                        <li>
                            <a href="#">法国</a>
                        </li>
                        <li>
                            <a href="#">英国</a>
                        </li>
                        <li>
                            <a href="#">俄罗斯</a>
                        </li>
                        <li>
                            <a href="#">意大利</a>
                        </li>
                        <li>
                            <a href="#">西班牙</a>
                        </li>
                        <li>
                            <a href="#">德国</a>
                        </li>
                        <li>
                            <a href="#">波兰</a>
                        </li>
                        <li>
                            <a href="#">澳大利亚</a>
                        </li>
                        <li>
                            <a href="#">伊朗</a>
                        </li>
                        <li>
                            <a href="#">其他</a>
                        </li>
                    </ul>
                </li>
                <li class="tags-line tags-line-border" >
                    <div class="tags-title">年代 :</div>
                    <ul class="tags">
                        <li class="active">
                            <a href="#" style="cursor: pointer">全部</a>
                        </li>
                        <li>
                            <a href="#">2029</a>
                        </li>
                        <li>
                            <a href="#">2028</a>
                        </li>
                        <li>
                            <a href="#">2027</a>
                        </li>
                        <li>
                            <a href="#">2026</a>
                        </li>
                        <li>
                            <a href="#">2025</a>
                        </li>
                        <li>
                            <a href="#">2024</a>
                        </li>
                        <li>
                            <a href="#">2023</a>
                        </li>
                        <li>
                            <a href="#">2022</a>
                        </li>
                        <li>
                            <a href="#">2021</a>
                        </li>
                        <li>
                            <a href="#">2020</a>
                        </li>
                        <li>
                            <a href="#">2019</a>
                        </li>
                        <li>
                            <a href="#">2018</a>
                        </li>
                        <li>
                            <a href="#">2017</a>
                        </li>
                        <li>
                            <a href="#">2016</a>
                        </li>
                        <li>
                            <a href="#">2015</a>
                        </li>
                        <li>
                            <a href="#">2014</a>
                        </li>
                        <li>
                            <a href="#">2013</a>
                        </li>
                        <li>
                            <a href="#">2012</a>
                        </li>
                        <li>
                            <a href="#">2011</a>
                        </li>
                        <li>
                            <a href="#">2000-2010</a>
                        </li>
                        <li>
                            <a href="#">90年代</a>
                        </li>
                        <li>
                            <a href="#">80年代</a>
                        </li>
                        <li>
                            <a href="#">70年代</a>
                        </li>
                        <li>
                            <a href="#">更早</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="main">
            <div class="mb-2 flex items-center text-sm main_top">
                <el-radio-group v-model="data.radio" class="ml-4">
                    <el-radio label="1" size="large">按热门排序</el-radio>
                    <el-radio label="2" size="large">按时间排序</el-radio>
                    <el-radio label="3" size="large">按评分排序</el-radio>
                </el-radio-group>
            </div>
            <div class="main_content">
                <div class="movie_box" v-for="item in allMovieInfo">
                    <img :src="item.imageUrl" @click="goToBuyTicket(item.name)" style="max-height: 240px;min-height: 240px">
                    <p>{{item.name}}</p>
                    <p>{{item.score}}</p>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
import {onMounted, reactive, ref, watch, watchEffect} from 'vue'
import router from '@/router'
import {
    findAllMovieInfo,
    findConditionMovie,
    getAllMovieInfo, getImageUrl,
    getImg, getImgUrl,
    getMovieCondition,
    setSelectHttpName
} from '@/http/movieApi'
import {useRoute} from 'vue-router'

export default {
    name: "movieViews",
    setup(){
        let allMovieInfo=ref()
        let data=reactive({
            type:"全部",
            location:"全部",
            year:"全部",
            radio:'1',
            index:'1',
        })
        let route=useRoute();
        const menu_default = ref('1');

        if (route.query.menu_default!==undefined){
            menu_default.value=route.query.menu_default;
        }
        function goToBuyTicket(movieName){
            router.push({name:"movieDetails",query:{movieDetailsData:movieName}})
        }
        //避免在页面还没加载完时 去获取元素
        function getATextAndChangeLiClass(){
            const tags = document.querySelectorAll('ul.tags');
            for (let i=0;i<tags.length;i++){
                const tagsLi = tags[i].querySelectorAll('li');
                tagsLi.forEach((li)=>{
                    li.addEventListener('click',(event)=>{
                        event.preventDefault(); // 阻止默认链接行为

                        // 如果点的a标签则获取a标签的父元素li
                        const clickedLi = event.target.nodeName === 'A' ? event.target.parentNode : event.target;

                        // 删除所有其他 li 元素上的 'active' 类
                        tagsLi.forEach((li) => {
                            if (li !== clickedLi) {
                                li.classList.remove('active');
                            }
                        });
                        clickedLi.classList.add('active'); // 添加 'active' 类到点击元素所在的 li 元素
                        //获取a标签里的文本
                        const clickedA =event.target.closest('a')

                        if (clickedA && clickedA.textContent) {
                            if (i===0){
                                data.type=clickedA.textContent.trim()
                            }else if (i===1){
                                data.location=clickedA.textContent.trim()
                            }else if(i===2){
                                data.year=clickedA.textContent.trim()
                            }
                        }else {
                            console.log("元素加载中")
                        }
                    })
                })
            }

            watch(data,()=>{
                setSelectHttpName("http://localhost:8181/Movie/findByConditionInfo")
                findConditionMovie(JSON.stringify(data)).then(async res => {
                    const movies = await Promise.all(res.map(async item => {
                        const imageUrl = await getImgUrl(item.name);
                        return {
                            ...item,
                            imageUrl: imageUrl,
                        };
                    }));
                    return movies;
                }).then(res=>{
                    allMovieInfo.value=res
                    return allMovieInfo.value;
                });
            })
        }
        setSelectHttpName("http://localhost:8181/Movie/findMovieAllInfo")
        findAllMovieInfo(allMovieInfo)

        let handleSelect=((index)=>{
            data.index=index;
        })

        onMounted(() => {
            getATextAndChangeLiClass();
        });
        return{
            goToBuyTicket,
            allMovieInfo,
            getImg,
            data,
            handleSelect,
            menu_default
        }
    }
}
</script>

<style lang="scss" scoped>
.top_menu{
    margin-top:1%;
    :nth-child(1){
       margin-left: 40%;
    }
}
.tags-panel {
    border: 1px solid #e5e5e5;
    padding: 0 20px;
    margin-top: 40px;
    margin-left: 10%;
    margin-right: 10%;
}
.tags-panel li, .tags-panel ul {
    margin: 0;
    padding: 0;
    list-style-type: none;
}

.tags-panel li, .tags-panel ul {
    margin: 0;
    padding: 0;
    list-style-type: none;
}
ul {
    display: block;
    list-style-type: disc;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-inline-start: 40px;
}
.tags-line {
    padding: 10px 0!important;
    border-bottom: rgb(200,200,200) dotted 1px;
}
.tags-title {
    height: 24px;
    line-height: 24px;
    float: left;
    color: #999;
    font-size: 14px;
}
.tags {
    margin-left: 40px!important;
}
.tags li.active {
    background: #f34d41;
    color: #fff;
}
.tags li {
    border-radius: 14px;
    padding: 3px 9px;
    display: inline-block;
    margin-left: 12px;
}
.tags li.active a {
    color: #fff;
}
.tags li a {
    color: #333;
    font-size: 14px;
}
a {
    text-decoration: none;
}
a {
    background-color: transparent;
    -webkit-text-decoration-skip: objects;
}

.main{
    float: left;
    width: 80%;
    margin-left: 10%;
}
.main_top{
    float: left;
    width: 100%;
    margin-top: 3%;
    margin-bottom: 3%;
}
.main_content{
    width: 100%;
    float: left;
    padding-left:3%;
}
.movie_box{
    width: 16%;
    float: left;
    margin-top: 1%;
    >img{
        float: left;
        width: 90%;
        margin-left: 5%;
    }
    >img:hover{
        cursor: pointer;
    }
    >p{
        float: left;
        width: 100%;
        margin-top: 4%;
    }
    >p:nth-child(2){
        font-size: 1rem;
        text-align: center;
    }
    >p:nth-child(3){
        color: #ffb400;
        text-align: center;
        font-size: 1.4rem;
        font-style: italic;
    }
}
</style>