<template>
    <div>
        <div class="heard">
            <div>
                <el-button link  icon="el-icon-arrow-left" style="color: black" @click="back"><el-icon><ArrowLeftBold /></el-icon>返回</el-button>
            </div>
            <div class="heard_content" >
                <div class="img">
                    <img src="@/assets/影院.png">
                </div>
                <div class="info">
                    <div class="info_p">
                        <p>{{buyData.name}}</p>
                        <p>{{buyData.location}}</p>
                        <p>电话：{{buyData.phone}}</p>
                    </div>
                    <div class="sever">
                        <p>影院服务</p>
                        <hr>
                        <ul class="sever_info">
                            <li>
                                <div>改签</div>
                                <p>未取票用户放映前60分钟可改签</p>
                            </li>
                            <li>
                                <div>儿童优惠</div>
                                <p>身高在1.2米（含1.2米）以上的儿童，需购买影票，每位成人可免费携带一位1.2米以下儿童！</p>
                            </li>
                            <li>
                                <div>WiFi</div>
                                <p>影院有免费WiFi</p>
                            </li>
                            <li>
                                <div>可停车</div>
                                <p>商场负一层300个免费车位</p>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="main">
            <el-breadcrumb :separator-icon="ArrowRight" style="font-size: 1.2rem;">
                <el-breadcrumb-item :to="{ path: '/Index' }">三柒影院</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/Cinema' }">影院</el-breadcrumb-item>
                <el-breadcrumb-item >{{buyData.name}}</el-breadcrumb-item>
            </el-breadcrumb>
            <div class="banner" id="banner">
                <ArrowLeft style="width: 60px;cursor: pointer" class="ArrowLeft" @click="prevSlide()"></ArrowLeft>
                <ul ref="ulElement" id="ulElement">
                    <li v-for="(item) in movieDatas">
                        <img :src="item.imageUrl" v-if="movieInfoDate.name===item.name" class="clicked" @click="changeLiStyle(item,$event)"  style="max-height: 240px">
                        <img :src="item.imageUrl" v-if="movieInfoDate.name!==item.name" @click="changeLiStyle(item,$event)" style="max-height: 180px">
                    </li>
                </ul>
                <ArrowRight style="width: 60px;cursor: pointer" class="ArrowRight" @click="nextSlide()"></ArrowRight>
            </div>
            <div class="movie_info"  v-if="movieInfoDate!=null">
                <h2>{{movieInfoDate.name}}</h2>
                <p> {{movieInfoDate.see}}
                    <span>想看</span>
                </p>
                <div v-if="movieInfoDate.movie_info!=null">
                    <p>时长 : <span>{{movieInfoDate.movie_info.play_time}}分钟</span></p>
                    <p>类型 : <span>{{movieInfoDate.movie_info.type}}</span> </p>
                    <p>主演 : <span>{{movieInfoDate.movie_info.starring}}</span></p>
                </div>
            </div>
            <hr style="float:left;width: 100%;margin-top: 20px">
            <div class="time">
                <span>观影时间：</span>
                <el-button
                    v-for="(item,index) in dateTime"
                    size="small"
                    round="round"
                    :class="{ button1: selectedIndex === index }"
                    @click="dateButton(item,index)"
                >
                    {{item.date}}
                </el-button>
            </div>
            <el-table
                :data="tableData"
                style="width: 100%"
            >
                <el-table-column prop="startTime" label="放映时间" width="180" />
                <el-table-column prop="type" label="语言版本" width="180" />
                <el-table-column prop="movieTheater" label="放映厅" />
                <el-table-column prop="price" label="售价（元）"  />
                <el-table-column label="选座购票" >
                    <template #default="scope">
                        <el-button size="small" round="round" class="button1" @click="buyButton(scope.row)">
                            选座购票
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import {useRoute} from 'vue-router'
import {onMounted, reactive, ref} from 'vue'
import {ArrowRight,ArrowLeft} from '@element-plus/icons'
import {
    findAllByCinemaId,
    findMovieInfoByName,
    getImg,
    postParamAndUrl,
    setSelectHttpName
} from '@/http/movieApi'
import router from '@/router'
import {post_findByCinemaIdAndByMovieName} from '@/http/schedulingApi'
import {compareTime, formatDate} from '@/util/date'
export default {
    name: "buyTicket",
    components: {
        ArrowRight,
        ArrowLeft,
    },
    setup(){
        const route=useRoute()
        let buyData=ref({})
        let movieInfoDate=ref()
        let movieDatas=ref([]);
        let selectedIndex=ref(0)
        const ulElement = ref(null);
        let currentSlide=ref(0)
        const moveLength=ref(0)
        const totalWidthIndex=ref(1)
        const scheduling=ref()
        let day=reactive({value:""})


        //---------------------------------获取数据-----------------------------------------
        buyData = JSON.parse(route.query.buyData);
        //把JSON.parse(route.query.movieInfoDate)数据变成一个响应式数据
        movieInfoDate.value=JSON.parse( route.query.movieInfoDate)
        const dateTime=ref([]);
        let tableData=ref([
            {
                startTime:"05:30",
                type:"日语2d",
                movieTheater:"4号影厅",
                price:"35",
            },
        ])

        //根据id获取排片数据
        setSelectHttpName("http://localhost:8181/CinemaAndScheduling/findSchedulingByCinemaId")
        findAllByCinemaId(scheduling, buyData.cinemaId).then(async res=>{
            scheduling.value=res
            if (movieInfoDate.value===null||movieInfoDate.value===""&&res.length>0){
                await findMovieInfoByName(
                    "http://localhost:8181/Movie/findMovieAllInfoByName",
                    res[0].name
                ).then(res=>{
                    movieInfoDate.value=res;
                });
            }
        })

        //获取所有电影的信息
        postParamAndUrl(
            "http://localhost:8181/Movie/findMovieAllInfoByCinema_name",
            movieDatas,
            buyData.cinemaId).then(res=>{
            movieDatas.value=res;
        })

        //获取当前影院当前影片排片信息
        function getCinema_MovieScheduling(cinemaId,movieName){
            post_findByCinemaIdAndByMovieName(
                "http://localhost:8181/CinemaAndScheduling/findSchedulingByMovieName",
                cinemaId,
                movieName
            ).then(res=>{
                dateTime.value=[]
                //获取排片信息大于等于今天
                for (let resKey in res) {
                    if (compareTime(res[resKey].date,formatDate(new Date()))>=0){
                        dateTime.value.push(res[resKey])
                    }
                }
                //获取当天的排片信息
                if (dateTime.value!==null){
                    tableData.value=[];
                    dateTime.value.forEach(res=>{
                        if (compareTime(res.date,formatDate(new Date()))===0){
                            tableData.value.push(res);
                        }
                    })
                    console.log("tableData.value:",tableData.value)
                }

                // 创建一个空对象来跟踪已经出现过的日期
                const uniqueDates = {};

                // 过滤重复日期并重新赋值给 dateTime.value
                dateTime.value = dateTime.value.filter(item => {
                    // 获取当前项的日期
                    const date = item.date;

                    // 如果当前日期在 uniqueDates 对象中不存在，则添加到 uniqueDates 中，并保留当前项
                    if (!uniqueDates[date]) {
                        uniqueDates[date] = true;
                        return true;
                    }

                    // 如果当前日期已经存在于 uniqueDates 中，则过滤掉当前项
                    return false;
                });
                // 对 dateTime.value 数组根据 date 属性进行排序
                dateTime.value.sort((a, b) => {
                    // 根据日期属性进行比较
                    if (a.date < b.date) return -1;
                    if (a.date > b.date) return 1;
                    return 0;
                });

            })
        }
        getCinema_MovieScheduling( buyData.cinemaId,movieInfoDate.value.name);

        //---------------------------------函数-----------------------------------------


        //给选中的li添加样式
        function changeLiStyle(item,event) {
            const uls = document.querySelector('#banner ul');
            let imgs=uls.querySelectorAll('li img')
            // 移除所有其他被点击的项的样式
            imgs.forEach(i => i.classList.remove('clicked'));

            // 添加被点击的项的样式
            event.target.classList.add('clicked');
            movieInfoDate.value=item
            //获取当前影院当前影片排片信息
            getCinema_MovieScheduling(buyData.cinemaId,movieInfoDate.value.name);

            currentSlide.value=item.id-1;
        }

        //设置ul的宽
        const setUlWidth = () => {
            if (ulElement.value) {
                ulElement.value.style.width = movieDatas.value.length*100/7+'%';
            } else {
                console.log(' ulElement没找到');
            }
        };

        //获取进来图片的编号
        let setCurrentSlide=()=>{
            setTimeout(()=>{
                movieDatas.value.forEach((item,index)=>{
                    if(movieInfoDate.value.name===item.name){
                        currentSlide.value=index;
                    }
                })
            },500)
        }

        // 切换至上一张图片
        function prevSlide() {
            // 获取图片轮播容器和图片
            const uls = document.querySelector('#banner ul');
            let imgs=uls.querySelectorAll('li img')

            if (currentSlide.value!==0){
                //清除样式
                imgs.forEach((index)=>{
                    index.classList.remove('clicked');
                })
                //添加样式
                currentSlide.value = (currentSlide.value - 1 + imgs.length) % imgs.length;
                imgs[currentSlide.value].classList.add('clicked');
                //更新movieInfoDate的值
                movieDatas.value.forEach((item,index)=>{
                    if (index===currentSlide.value){
                        movieInfoDate.value=item
                    }
                });
                //获取当前影院当前影片排片信息
                getCinema_MovieScheduling(buyData.cinemaId,movieInfoDate.value.name);
            }
            const bannerDiv = document.getElementById('banner');
            const totalWidth = bannerDiv.offsetWidth;
            const ulElementWidth=document.getElementById('ulElement').offsetWidth;
            if (currentSlide.value!==0){
                const ulElementLiWidth=uls.querySelectorAll('li')[currentSlide.value].offsetWidth
                moveLength.value-=ulElementLiWidth+20
                const len=(uls.querySelectorAll('li')[0].offsetWidth+20)*2
                if ( moveLength.value + len < (totalWidthIndex.value)*totalWidth){
                    //移动一个ul的距离
                    ulElement.value.style.transform = `translateX(` + -((totalWidthIndex.value-1)*totalWidth/ulElementWidth)*100 + `%)`;
                    totalWidthIndex.value--
                }
            }else{
                ulElement.value.style.transform = `translateX(0px)`;
                moveLength.value=0
            }

        }
        // 切换至下一张图片
        function nextSlide() {
            // 获取图片轮播容器和图片
            const uls = document.querySelector('#banner ul');
            let imgs=uls.querySelectorAll('li img')
            if (currentSlide.value!==imgs.length-1){
                imgs.forEach((index)=>{
                    index.classList.remove('clicked');
                })
                currentSlide.value = (currentSlide.value + 1 + imgs.length) % imgs.length;
                imgs[currentSlide.value].classList.add('clicked');
                //更新movieInfoDate的值
                movieDatas.value.forEach((item,index)=>{
                    if (index===currentSlide.value){
                        movieInfoDate.value=item
                    }
                });
                //获取当前影院当前影片排片信息
                getCinema_MovieScheduling(buyData.cinemaId,movieInfoDate.value.name);
            }
            const bannerDiv = document.getElementById('banner');
            const totalWidth = bannerDiv.offsetWidth;
            const ulElementWidth=document.getElementById('ulElement').offsetWidth;
            if (currentSlide.value!==imgs.length-1){
                const ulElementLiWidth=uls.querySelectorAll('li')[currentSlide.value].offsetWidth
                moveLength.value+=ulElementLiWidth+20
                const len=(uls.querySelectorAll('li')[0].offsetWidth+20)*2
                if ( moveLength.value + len>totalWidthIndex.value*totalWidth){

                    //移动一个ul的距离
                    ulElement.value.style.transform = `translateX(` + -((totalWidthIndex.value)*totalWidth/ulElementWidth)*100 + `%)`;
                    totalWidthIndex.value++
                }
            }

        }
        //获取点击日期排片数据和样式
        const dateButton = (item,index) => {
            selectedIndex.value = index;
            day.value=item.date;
            tableData.value=[];

            post_findByCinemaIdAndByMovieName(
                "http://localhost:8181/CinemaAndScheduling/findSchedulingByMovieName",
                buyData.cinemaId,
                movieInfoDate.value.name
            ).then(res=>{
                for (let resKey in res) {
                    if (compareTime(res[resKey].date,item.date)===0){
                        tableData.value.push(res[resKey]);
                    }
                }
            })

        };
        //跳转购票页面并传递数据
        let buyButton=(row)=>{
            router.push(
                {
                    name: "selectSeat",
                    query:{
                        movieInfoDate:JSON.stringify(movieInfoDate.value),
                        cinemaData:JSON.stringify(row),
                        buyData:JSON.stringify(buyData),
                        day:JSON.stringify(day),
                    }
                }
            )
        }
        //返回上一个页面
        let back=()=>{
            router.go(-1);
        }

        onMounted(() => {
            setUlWidth();
            setCurrentSlide();
        });



        return{
            buyData,
            movieInfoDate,
            dateTime,
            ArrowRight,
            tableData,
            dateButton,
            buyButton,
            selectedIndex,
            ulElement,
            movieDatas,
            scheduling,
            getImg,
            prevSlide,
            nextSlide,
            changeLiStyle,
            back
        }
    }
}
</script>

<style scoped lang="scss">
ul li{
    list-style: none;
}
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
    width: 25%;
    float: left;
    border:  solid 5px;
    >img{
        width: 100%;
        float: left;
    }
}
.info{
    width: 48%;
    float: left;
}
.info_p{
    width: 100%;
    float: left;
    margin-top: 5%;
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
.sever{
    width: 100%;
    margin-top: 5%;
    margin-left: 5%;
    >p{
        float: left;
    }
    >hr{
        margin-top: 2%;
        float: right;
        width: 85%;
    }
}
.sever_info{
    width: 100%;
    margin-top: 5%;
    >li{
        float: left;
        width: 100%;
        margin-top: 10px;
        >div{
            float: left;
            border: #9299a1 solid 1px;
            padding: 5px;
            margin-right: 10px;
            font-size: 0.6rem;
        }
        >p{
            margin-top: 5px;
            float: left;
            font-size: 0.6rem;
        }
    }

}

.main{
    width: 80%;
    float: left;
    margin-left: 10%;
    margin-top: 40px;
}
.banner{
    margin-top: 20px;
    padding: 15px;
    background: rgb(245,245,245);
    float: contour;
    overflow: hidden;
    position: relative;
    >ul{
        float: left;
        min-width: 400%;
        margin-left: 40px;
        >li{
            float: left;
            margin-top: 20px;
            margin-right: 20px;
            >img{
                width: 80%;
                transition: all 0.4s ease-in-out;
            }
            >img.clicked {
                transform: scale(1.2);
                border: 4px solid rgb(242,64,48);
            }
        }

    }
}
.movie_info{
    display: inline-block;
    width: 100%;
    >h2{
        float: left;
        margin-right: 10px;
        margin-top: 20px;
        font-size: 1.6rem;
        font-weight: normal;
    }
    >p{
        float: left;
        color: #ffb400;
        margin-top: 25px;
        font-size: 1.5rem;
        >span{
            font-size: 1.2rem;
        }
    }
    >div{
        width: 100%;
        float: left;
        margin-top: 20px;
        >p{
            float: left;
            font-size: 1rem;
            margin-right: 15px;
            color: #9299a1;
            >span{
                font-size: 1.1rem;
                color: black;
            }
        }
    }

}

.time{
    width: 100%;
    float: left;
    margin-top: 20px;
    margin-bottom: 20px;
    >span{
        float: left;
        color: #9299a1;
        margin-right: 20px;
    }
}
.button1{
    background: #f34d41 !important;
    color: white !important;
}
.ArrowLeft{
    left: 0;
    top: 80px;
    position: absolute;
    z-index: 255;
}
.ArrowRight{
    right: 0;
    top: 80px;
    position: absolute;

}
</style>