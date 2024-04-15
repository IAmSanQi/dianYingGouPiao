<template>
    <div style="float: left">
        <div class="heard">
            <div>
                <el-button link  icon="el-icon-arrow-left" style="color: black" @click="back"><el-icon><ArrowLeftBold /></el-icon>返回</el-button>
            </div>
            <div class="heard_content" v-if="movieInfoDate!=null">
                <div class="img">
                    <el-image
                        :src="imageUrl"
                        style="width: 90%;"
                    />
                </div>
                <div class="info">
                    <div class="info_p">
                        <p>{{movieInfoDate.name}}</p>
                        <p>{{movieInfoDate.movie_info.type}}</p>
                        <p>{{movieInfoDate.movie_info.location}}/{{movieInfoDate.movie_info.play_time}}分钟</p>
                        <p>{{movieInfoDate.movie_info.timeString}}中国大陆上映</p>
                    </div>
                    <div class="want">
                        <div>
                            <a href="#"><p>想看</p></a>
                        </div>
                        <div>
                            <a href="#"><p>评分</p></a>
                        </div>
                    </div>
                    <div class="buy">
                        <a href="#" @click="movieTicket(movieInfoDate.name)"><p>查看电影详情</p></a>
                    </div>
                </div>
                <div class="side">
                    <div class="score">
                        <p>评分</p>
                        <p>{{movieInfoDate.score}}</p>
                    </div>
                    <div class="ticket">
                        <p>票房</p>
                        <p>{{movieInfoDate.ticketNumber}}</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="tags-panel">
            <ul class="tags-lines">
                <li class="tags-line">
                    <div class="tags-title">品牌:</div>
                    <ul class="tags" id="brand">
                        <li class="active">
                            <a href="#" style="cursor: pointer" @click="changeLiClass('全部',$event)">全部</a>
                        </li>
                        <li v-for="(item) in brand">
                            <a href="#" @click="changeLiClass(item,$event)">{{item}}</a>
                        </li>
                        <li>
                            <a href="#">其他</a>
                        </li>
                    </ul>
                </li>
                <li class="tags-line tags-line-border">
                    <div class="tags-title">行政区:</div>
                    <ul class="tags" id="administrativeDistrict">
                        <li class="active">
                            <a href="#" style="cursor: pointer" @click="changeLiClass('全部',$event)">全部</a>
                        </li>
                        <li v-for="(item) in administrativeDistrict">
                            <a href="#" @click="changeLiClass(item,$event)">{{item}}</a>
                        </li>
                    </ul>
                </li>
                <li class="tags-line tags-line-border" >
                    <div class="tags-title">影厅类型:</div>
                    <ul class="tags" id="type">
                        <li class="active">
                            <a href="#" style="cursor: pointer" @click="changeLiClass('全部',$event)">全部</a>
                        </li>
                        <li v-for="(item) in type">
                            <a href="#" @click="changeLiClass(item,$event)">{{item}}</a>
                        </li>
                    </ul>
                </li>
                <li class="tags-line tags-line-border" >
                    <div class="tags-title">影院服务:</div>
                    <ul class="tags" id="service">
                        <li class="active">
                            <a href="#" style="cursor: pointer" @click="changeLiClass('全部',$event)">全部</a>
                        </li>
                        <li>
                            <a href="#" @click="changeLiClass('1',$event)">可改签</a>
                        </li>
                        <li>
                            <a href="#" @click="changeLiClass('2',$event)">可退票</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="main">
            <div class="main_header">
                <span>影院列表</span>
            </div>
            <div class="main_list" v-for="item in cinemaInfo">
                <div class="text" v-if="item!=null">
                    <p>{{item.name}}</p>
                    <p>地址：{{item.location}}</p>
                </div>
                <div class="many" v-if="item!=null">
                    <el-button class="button" round="round" @click="goToBuyTicket(item)">选座购票</el-button>
                    <p>￥{{item.price}}起</p>
                </div>
            </div>
            <div v-if="cinemaInfo.length===0" style="float: left;width: 100%;margin-top: 40px">抱歉，没有找到相关结果，请尝试用其他条件筛选。</div>
        </div>
    </div>
</template>

<script>
import {useRoute} from 'vue-router'
import { reactive, ref} from 'vue'
import {findMovieInfo, getMovieInfo, setSelectHttpName, getImg, findMovieInfoByName, getImgUrl} from '@/http/movieApi'
import {getData, selectCinema,setSelectHttpName as CSetSelectHttpName} from '@/http/cinemaApi'
import router from '@/router'
import {post_findByMovie_name} from '@/http/schedulingApi'
export default {
    name: "ticketing",
    setup(){
        let route=useRoute();
        let movieName="";
        const movieInfoDate=ref();
        const imageUrl=ref()
        movieName=route.query.ticketing;
        findMovieInfoByName(
            "http://localhost:8181/Movie/findMovieAllInfoByName",
            movieName
        ).then(res=>{
            movieInfoDate.value=res;
        });
        getImgUrl(movieName).then(url=>{
            imageUrl.value=url;
        })
        let movieTicket=(name)=>{
            router.push({name: "movieDetails", query: {movieDetailsData: name}})
        }

        let cinemaInfo=ref([]);
        let brand=ref([]);
        let administrativeDistrict=ref([]);
        let type=ref([]);
        let data=reactive({
            brandInfo:"全部",
            administrativeDistrictInfo:"全部",
            typeInfo:"全部",
            serviceInfo:"全部",
        })
        post_findByMovie_name(
            "http://localhost:8181/Cinema/selectCinemaByScheduling",
            movieName,
            data.brandInfo,
            data.administrativeDistrictInfo,
            data.typeInfo,
            data.serviceInfo,
        ).then(res=>{
            cinemaInfo.value=res;
        })
        CSetSelectHttpName("http://localhost:8181/Cinema/brandFilter")
        brand.value=getData(brand)
        CSetSelectHttpName("http://localhost:8181/Cinema/administrativeDistrictFilter")
        administrativeDistrict.value=getData(administrativeDistrict)
        CSetSelectHttpName("http://localhost:8181/Cinema/typeFilter")
        type.value=getData(type)

        let changeLiClass = (item,event) => {
            const clickedLi = event.target.closest('li'); // 获取最近的父级 li 元素
            const tag = clickedLi.closest('ul'); // 获取最近的父级 ul 元素
            const tagsLi = tag.querySelectorAll('li'); // 获取最近的父级 ul 元素里所有的li元素

            //删除当前ul里所有的li的active类
            tagsLi.forEach((li) => {
                li.classList.remove('active');
            });

            //给当前的li添加active类
            clickedLi.classList.add('active');

            const ulId = tag.getAttribute('id');
            switch (ulId) {
                case "brand":
                    data.brandInfo = item;
                    break;
                case "administrativeDistrict":
                    data.administrativeDistrictInfo = item;
                    break;
                case "type":
                    data.typeInfo = item;
                    break;
                case "service":
                    data.serviceInfo = item;
                    break;
            }
            post_findByMovie_name(
                "http://localhost:8181/Cinema/selectCinemaByScheduling",
                movieName,
                data.brandInfo,
                data.administrativeDistrictInfo,
                data.typeInfo,
                data.serviceInfo,
            ).then(res=>{
                cinemaInfo.value=res;
            })
        };

        let goToBuyTicket=(item)=>{
            router.push({name:"buyTicket",query:{buyData:JSON.stringify(item),movieInfoDate:JSON.stringify(movieInfoDate.value)}})
        }
        let back=()=>{
            router.go(-1);
        }
        return{
            movieInfoDate,
            movieName,
            imageUrl,
            getImg,
            cinemaInfo,
            brand,
            administrativeDistrict,
            type,
            changeLiClass,
            data,
            movieTicket,
            goToBuyTicket,
            back
        }
    }
}
</script>

<style lang="scss" scoped>
.heard{
    width: 100%;
    float: left;
    background: rgba(156,299,241,0.4);
}
.heard_content{
    width: 80%;
    float: left;
    margin-left: 10%;
    margin-top: 3%;
}
.img{
    width: 20%;
    float: left;
    >img{
        width: 100%;
    }
}
.info{
    width: 38%;
    float: left;
}
.info_p{
    width: 100%;
    float: left;
    margin-top: 10%;
    margin-left: 5%;
    >p{
        font-family: "新宋体";
    }
    >p:nth-child(1){
        font-size: 1.8rem;
        font-weight: bold;
    }
    >p:nth-child(2){
        margin-top: 4%;
    }
}
.want{
    width: 100%;
    float: left;
    margin-left: 5%;
    margin-top: 10%;
    >div{
        width: 28%;
        float: left;
        background: rgba(117,97,137,0.8);
        text-align: center;
        margin-right: 5%;
        >a{
            width: 100%;
            float: left;
            padding: 5%;
            >p{
                width: 100%;
                float: left;
                color: white;
            }
        }
    }

}
.buy{
    width: 61%;
    float: left;
    margin-left: 5%;
    margin-top: 5%;
    background: red;
    text-align: center;
    >a{
        width: 100%;
        float: left;
        >p{
            width: 100%;
            float: left;
            padding: 3%;
            color: white;
        }
    }
}
.side{
    width: 30%;
    float: left;
}
.score{
    margin-top: 50%;
    >p:nth-child(1){
        font-size: 0.8rem;
    }
    >p:nth-child(2){
        color: #ffb400;
        font-size: 1.6rem;
    }
}
.ticket{
    margin-top: 3%;
    >p:nth-child(1){
        font-size: 0.8rem;
    }
    >p:nth-child(2){
        font-weight: bold;
        font-size: 1.6rem;
    }
}

.tags-panel {
    border: 1px solid #e5e5e5;
    padding: 0 20px;
    margin-top: 40px;
    margin-left: 10%;
    margin-right: 10%;
    float: left;
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
    background-color: transparent;
    -webkit-text-decoration-skip: objects;
}
.main{
    margin-top: 40px;
    margin-left: 10%;
    margin-right: 10%;
}
.main_header{
    border-left:#f34d41 solid 5px;
    float: left;
    >span{
        font-size: 1.5rem;
        margin-left: 5px;
    }
}
.main_list{
    float: left;
    width: 100%;
    .text{
        width: 80%;
        float: left;
        >p:nth-child(1){
            margin-top: 20px;
            font-size: 1.1rem;
        }
        >p:nth-child(2){
            font-size: 0.9rem;
            color: #9299a1;
        }
    }
    .many{
        float: right;
        width: 20%;
        >p{
            float: right;
            margin-top: 8px;
            margin-right: 10px;
            text-align: center;
            color: #f34d41;
        }
        .button{
            float: right;
            background: #f34d41;
            color: white;
        }
    }
}
</style>