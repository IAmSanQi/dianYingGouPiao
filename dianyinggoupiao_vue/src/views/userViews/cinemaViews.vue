<template>
    <div>
        <div class="tags-panel">
            <ul class="tags-lines">
                <li class="tags-line">
                    <div class="tags-title">品牌: </div>
                    <ul class="tags" id="brand">
                        <li class="active">
                            <a href="#" style="cursor: pointer" @click="changeLiClass('全部',$event)">全部</a>
                        </li>
                        <li v-for="(item) in brand">
                            <a href="#" @click="changeLiClass(item,$event)">{{item}}</a>
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
            <div class="main_list" v-for="item in cinemaInfo.value">
                <div class="text" v-if="item!=null">
                    <p>{{item.name}}</p>
                    <p>地址：{{item.location}}</p>
                </div>
                <div class="many" v-if="item!=null">
                    <el-button class="button" round="round" @click="goToBuyTicket(item)">选座购票</el-button>
                    <p>￥{{item.price}}起</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {reactive, ref} from 'vue'
import {setSelectHttpName as MsetSelectHttpName, findMovieInfo, findMovieInfoByName} from '@/http/movieApi'
import {getData, selectCinema, setSelectHttpName} from '@/http/cinemaApi'
import router from '@/router'

export default {
    name: "cinemaViews",
    setup(){
        let cinemaInfo=reactive([]);
        let brand=ref([]);
        let administrativeDistrict=ref([]);
        let type=ref([]);
        let data=reactive({
            brandInfo:"全部",
            administrativeDistrictInfo:"全部",
            typeInfo:"全部",
            serviceInfo:"全部",
        })
        setSelectHttpName("http://localhost:8181/Cinema/findCinemaAll")
        cinemaInfo.value=getData(cinemaInfo)
        setSelectHttpName("http://localhost:8181/Cinema/brandFilter")
        brand.value=getData(brand)
        setSelectHttpName("http://localhost:8181/Cinema/administrativeDistrictFilter")
        administrativeDistrict.value=getData(administrativeDistrict)
        setSelectHttpName("http://localhost:8181/Cinema/typeFilter")
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
            console.log(data);
            setSelectHttpName("http://localhost:8181/Cinema/selectCinema")
            cinemaInfo.value=selectCinema(JSON.stringify(data),cinemaInfo)
        };

        let goToBuyTicket=(item)=>{
            router.push({name:"buyTicket",query:{buyData:JSON.stringify(item),movieInfoDate:JSON.stringify("")}})
        }
        return{
            cinemaInfo,
            brand,
            administrativeDistrict,
            type,
            changeLiClass,
            data,
            goToBuyTicket,
        }
    }
}
</script>

<style scoped lang="scss">
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