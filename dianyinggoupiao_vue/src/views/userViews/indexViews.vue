<template>
    <div>
        <div class="main">
            <div class="smash">
                <h1>正在热映({{countBlockbuster}}部)<span @click="goToMovie('1')"><a style="cursor: pointer;">全部></a></span></h1>
                <div v-for="item in movieTicketEight">
                    <div class="movieEight">
                        <div class="movieEight_img" v-if="item.name!=null">
                            <el-image :src="item.imageUrl" style="min-height: 180px;"/>
                            <div class="movieEight_text">
                                <span>{{item.name}}</span>
                                <i>{{item.score}}</i>
                            </div>
                        </div>
                        <div class="movieEight_ticket" @click="buyMovie(item.name)">
                            <a href="#">购票</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="smash">
                <h1 style="color: blue">即将上映({{countRelease}}部)
                    <span  @click="goToMovie('2')">
                        <a style="cursor: pointer; color: blue;text-decoration-color: blue;">全部></a>
                    </span>
                </h1>
                <div v-for="item in movieSeeEight">
                    <div class="movieEight" >
                        <div class="movieEight_img" v-if="item.name!=null">
                            <el-image :src="item.imageUrl" style="min-height: 180px;"/>
                            <div class="movieEight_text">
                                <span>{{item.name}}</span>
                                <i>{{item.score}}</i>
                            </div>
                        </div>
                        <div class="Release_ticket">
                            <div><a href="#">预告片</a></div>
                            <div @click="buyMovie(item.name)"><a href="#">预售</a></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="screen">
                <div class="screen_head">
                    <h1>热播电影</h1>
                    <ul>
                        <li><a>爱情</a></li>
                        <li><a>喜剧</a></li>
                        <li><a>动作</a></li>
                        <li><a>恐怖</a></li>
                        <li><a>动画</a></li>
                    </ul>
                    <span @click="goToMovie('1')"><a style="cursor: pointer;">全部></a></span>
                </div>
                <a href="#"><div class="screen_one" @click="searchMovie('5月天诺亚方舟')">
                    <img src="@/assets/5月天诺亚方舟大.png">
                    <div class="screen_one_text">
                        <span>5月天诺亚方舟</span>
                        <i>9.7</i>
                    </div>
                </div></a>
                <div v-for="item in movieScoreSix">
                    <a href="#">
                        <div class="movieSix">
                            <div class="movieSix_img" v-if="item.name!=null" @click="searchMovie(item.name)">
                                <el-image :src="item.imageUrl" style="min-height: 180px;"/>
                                <div class="movieSix_text">
                                    <span>{{item.name}}</span>
                                    <i>{{item.score}}</i>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <div class="aside">
            <div class="boxOffice">
                <h1>今日票房</h1>
                <div class="movieOne" v-if="movieTicketFive && movieTicketFive.length>1" @click="searchMovie(movieTicketFive[0].name)">
                    <div v-if="movieTicketFive.length>1" >
                        <i class="ranking-index">1</i>
                        <img :src="movieTicketFive[0].imageUrl"/>
                    </div>
                    <span>{{movieTicketFive[0].name}}</span>
                    <br>
                    <span STYLE="color: red">{{movieTicketFive[0].ticketNumber}}</span>
                </div>
                <div class="movieFive">
                    <ul v-for="(item,index) in movieTicketFive">
                        <li class="movieFive_li" v-if="index !== 0" @click="searchMovie(item.name)">
                            <a href="#">
                                <div class="index">{{index+1}}</div>
                                <div class="name">{{item.name}} </div>
                                <div class="span"><span>{{item.ticketNumber}}</span></div>
                            </a>
                        </li>

                    </ul>
                </div>
            </div>
            <div class="expectation">
                <h1>最受期待<span><a href="#">查看完整榜单></a></span></h1>
                <div class="expectationOne" v-if="movieSeeTen && movieSeeTen[0].name!=null" @click="searchMovie(movieSeeTen[0].name)">
                    <div>
                        <i class="ranking-index">1</i>
                        <img :src="movieSeeTen[0].imageUrl"/>
                    </div>
                    <div class="expectationOne_p">
                        <p>{{movieSeeTen[0].name}}</p>
                        <p><span>上映时间：</span>2023-6-9</p>
                        <p>{{movieSeeTen[0].see}} <span>想看</span></p>
                    </div>
                </div>
                <div class="expectationTwo" >
                    <div v-if="movieSeeTen && movieSeeTen.length>3" class="expectationTwo_left" @click="searchMovie(movieSeeTen[1].name)">
                        <div>
                            <i class="ranking-index">2</i>
                            <img :src="movieSeeTen[1].imageUrl"/>
                        </div>
                        <p>{{movieSeeTen[1].name}}</p>
                        <p class="expectationTwo_p">{{movieSeeTen[1].see}} <span>想看</span></p>
                    </div>
                    <div v-if="movieSeeTen && movieSeeTen.length>3" class="expectationTwo_right" @click="searchMovie(movieSeeTen[2].name)">
                        <div>
                            <i class="ranking-index">3</i>
                            <img :src="movieSeeTen[2].imageUrl"/>
                        </div>
                        <p>{{movieSeeTen[2].name}}</p>
                        <p class="expectationTwo_p">{{movieSeeTen[2].see}} <span>想看</span></p>
                    </div>
                </div>
                <div class="expectationThree" v-if="movieSeeTen && movieSeeTen.length>3">
                    <ul v-for="(item,index) in movieSeeTen">
                        <li v-if="index >2 " @click="searchMovie(item.name)">
                            <a href="#">
                                <div>{{index+1}}</div>
                                <div>{{item.name}} </div>
                                <div><span>{{item.see}}人想看</span></div>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="Top">
                <h1>TOP 100 <span><a href="#">查看完整榜单></a></span></h1>
                <div class="movieOne" v-if="movieScoreTen && movieScoreTen.length>1" @click="searchMovie(movieScoreTen[0].name)">
                    <div>
                        <i class="ranking-index">1</i>
                        <img :src="movieScoreTen[0].imageUrl"/>
                    </div>
                    <span>{{movieScoreTen[0].name}}</span>
                    <br>
                    <span STYLE="color: red">{{movieScoreTen[0].score}}分</span>
                </div>
                <div class="expectationThree" v-if="movieScoreTen && movieScoreTen.length>2">
                    <ul v-for="(item,index) in movieScoreTen">
                        <li v-if="index>0" @click="searchMovie(item.name)">
                            <a href="#">
                                <div>{{index+1}}</div>
                                <div>{{item.name}} </div>
                                <div><span>{{item.score}}分</span></div>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="none"></div>
        </div>
    </div>
</template>

<script>
import {reactive, ref} from 'vue'
import router from '@/router'
import {
    setSelectHttpName,
    findMovie,
    getMovieTicketFive,
    getMovieSeeEight,
    getMovieTicketEight,
    getMovieScoreSix,
    getMovieSeeTen,
    getMovieScoreTen,
    getImg,
    getImageUrl,
    getImgUrl, getCount
} from "@/http/movieApi"

export default {
    name: "indexViews",
    setup: function () {
        const movieTicketFive=ref()
        setSelectHttpName("http://localhost:8181/Movie/MovieTicketFive")
        findMovie(movieTicketFive)

        const movieTicketEight=ref();
        setSelectHttpName("http://localhost:8181/Movie/MovieTicketEight")
        findMovie(movieTicketEight)

        const movieSeeEight=ref();
        setSelectHttpName("http://localhost:8181/Movie/MovieSeeEight")
        findMovie(movieSeeEight)


        const movieScoreSix=ref();
        setSelectHttpName("http://localhost:8181/Movie/MovieScoreSix")
        findMovie(movieScoreSix)


        const movieSeeTen=ref();
        setSelectHttpName("http://localhost:8181/Movie/MovieSeeTen")
        findMovie(movieSeeTen)


        const movieScoreTen=ref();
        setSelectHttpName("http://localhost:8181/Movie/MovieScoreTen")
        findMovie(movieScoreTen)

        const countBlockbuster=ref(0);
        const countRelease=ref(0);
        getCount("http://localhost:8181/Movie/getBlockbusterLen").then(res=>{
            console.log("BlockbusterLen:",res)
            countBlockbuster.value=res;
        })
        getCount("http://localhost:8181/Movie/getReleaseLen").then(res=>{
            console.log("ReleaseLen:",res)
            countRelease.value=res;
        })

        function searchMovie(str) {
            router.push({name: "search", query: {searchData: str}})
        }
        function buyMovie(str) {
            router.push({name: "movieDetails", query: {movieDetailsData: str}})
        }

        const goToMovie=(index)=>{
            router.push({name: "movie", query: {menu_default: index}})
        }


        return {
            router,
            getImg,
            movieTicketFive,
            movieTicketEight,
            movieSeeEight,
            movieScoreSix,
            movieSeeTen,
            movieScoreTen,
            countBlockbuster,
            countRelease,
            searchMovie,
            buyMovie,
            getImgUrl,
            goToMovie,
        }
    },

}
</script>

<style lang="scss" scoped>
*{
    margin: 0;
    padding: 0;
}
a{
    text-decoration: none; /* 去除默认的下划线 */
    outline: none;	/* 去除旧版浏览器的点击后的外虚线框 */
    color: #000;	/* 去除默认的颜色和点击后变化的颜色 */
}
ul li{
    list-style: none;
}

//主体
.main{
    width: 70%;
    float: left;
    margin-top: 3%;
}

//热映
.smash{
    width: 80%;
    margin-left: 10%;
    margin-right: 10%;
    float: left;
    >h1{
        text-align: left;
        font-size: 1.8rem;
        color: red;
        margin-bottom: 5%;
        width: 100%;
        float: left;
        >span{
            float: right;
            >a{
                font-size: 1.2rem;
                color: red;
            }
            >a:hover{
                border-bottom: 3px solid red;
            }
        }
    }
    font-size: 1.2rem;
    margin-bottom: 10%;
}
.movieEight{
    width: 25%;
    float: left;
    position: relative;
}
.movieEight_img{
    width: 80%;
    margin-top: 5% ;
    float: left;
    >img{
        float: left;
        z-index: -1;
    }

}
.movieEight_text{
    width: 80%;
    position: absolute;
    bottom: 20%;
    z-index: 1;
    color: white;
    font-size: 1rem;
    >span{
        width: 80%;
        float: left;
        font-weight: bolder;
    }
    >i{
        width: 20%;
        float: right;
        background: #ffb400;
    }
}
.movieEight_ticket{
    float: left;
    width: 70%;
    text-align: center;
    padding: 5%;
    border-bottom: #efefef solid 0.5px;
    border-left: #efefef solid 0.5px;
    border-right: #efefef solid 0.5px;
}
.movieEight_ticket:hover{
    background: red;
}
//上映
.Release_ticket{
    float: left;
    width: 80%;
    text-align: center;
    border-bottom: #efefef solid 0.5px;
    border-left: #efefef solid 0.5px;
    border-right: #efefef solid 0.5px;
    div:nth-child(1){
        float: left;
        width: 39%;
        padding: 5%;
        border-right: #999999 solid 1px;
        >a:hover{
            color: blue;
        }
    }
    div:nth-child(2){
        width: 39%;
        padding: 5%;
        float: left;
        >a:hover{
            color: blue;
        }
    }
    >div>a{
        color: #999999;
    }
    >div:hover{
        background: #e5f9fe;
    }
}

//热播
.screen{
    width: 80%;
    margin-left: 10%;
    margin-right: 10%;
    float: left;
}
.screen_head{
    width: 100%;
    float: left;
    >h1{
        float: left;
        width: 20%;
        font-size: 1.8rem;
        color: red;
        margin-bottom: 5%;
    }
    >ul{
        width: 50%;
        float: left;
        margin-bottom: 5%;
        >li{
            float: left;
            margin-top: 3%;
            width: 20%;
            >a:hover{
                border-bottom: #000000 solid 0.5px;
            }
        }
    }
    >span{
        width: 30%;
        float: right;
        margin-bottom: 5%;
        >a{
            margin-top: 3%;
            color: red;
            float: right;
        }
    }
}
.screen_one{
    width: 50%;
    float: left;
    position: relative;
    >img{
        z-index: 1;
        float: left;
    }
}
.screen_one_text{
    position: absolute;
    width: 88%;
    float: left;
    z-index: 2;
    bottom: 0;
    >span{
        float: left;
        color: white;
    }
    >i{
        float: right;
        width: 10%;
        background: #ffb400;
    }
}
.movieSix{
    width: 25%;
    float: left;
    margin-bottom: 5%;
}
.movieSix_img{
    width: 100%;
    position: relative;
    float: left;
    >a{
        float: left;
    }
    >img{
        float: left;
    }
}
.movieSix_text{
    width: 100%;
    position: absolute;
    bottom: 0;
    >span{
        width: 60%;
        float: left;
        color: white;
        font-weight: bolder;
        background: linear-gradient(to top, #555555, rgb(255,255,255));
        opacity: 0.9;
    }
    >i{
        width: 20%;
       float: right;
        margin-right: 20%;
        background:  #ffb400;
    }
}

//边上
.aside{
    width: 30%;
    float: right;
    margin-top: 3%;
}

//票房
.boxOffice{
      width: 100%;
>h1{
    text-align: left;
    font-size: 1.6rem;
    color: red;
    margin-bottom: 5%;
}
font-size: 1.2rem;
float: right;
margin-bottom: 15%;

}
.movieOne{
    width: 98%;
    float: left;
    height: 100px;
    margin-bottom: 5%;
    overflow: hidden;
    border: #e5f9fe solid 0.1px;
>div img{
    width: 30%;
    height: 100%;
    float: left;
}
>span{
    margin-left: 2%;
    width: 65%;
    text-align: left;
    margin-top: 1%;
    float: right;
}
}
.movieOne:hover{
    background: #e6f1ff;
}
.ranking-index{
    position: absolute;
    padding-left: 4px;
    width: 16px;
    line-height: 20px;
    color: #fff;
    background-color: #ffb400;
}
.movieFive{
    width: 100%;
}
.movieFive_li{
    float: left;
    width: 98%;
    margin-bottom: 2%;
}
.movieFive_li:hover{
    background: #e6f1ff;
}
.index{
    width: 10%;
    float: left;
    font-family: "华文仿宋",serif;
    font-style: italic;
}
.name{
    width: 60%;
    float: left;
    font-family: "华文仿宋",serif;
    font-weight: bolder;
}
.span{
    width: 30%;
    float: right;
    color: red;
>span{float: right;}
}

//最受期待
.expectation{
    width: 100%;
    >h1{
        text-align: left;
        font-size: 1.6rem;
        color: #ffb400;
        margin-bottom: 5%;
        width: 100%;
        float: left;
        >span{
            float: right;
            >a{
                font-size: 1.2rem;
                color: #ffb400;
            }
            >a:hover{
                border-bottom: 3px solid #ffb400;
            }
        }
    }
    font-size: 1.2rem;
    margin-bottom: 10%;
}
.expectationOne{
    width: 98%;
    float: left;
    margin-bottom: 5%;
    border: #e5f9fe solid 0.1px;
> div img {
    width: 30%;
    height: 100%;
    float: left;
}
.expectationOne_p{
    margin-left: 2%;
    width: 65%;
    text-align: left;
    float: right;
p:nth-child(1){
    margin-top: 20%;
    font-size: 1.4rem;
}
p:nth-child(2){
    color: #9299a1;
}
p:nth-child(3){
    color: #ffb400;
}
}
}
.expectationOne:hover{
    background: #e6f1ff;
}
.expectationTwo{
    width: 100%;
    float: left;

    .expectationTwo_left{
        width: 40%;
        float: left;
        border: #e5f9fe solid 1px;
        >div {
            float: left;
            width: 100%;
            height: 100px;
            overflow: hidden;
        }
    }
    .expectationTwo_right{
        width: 40%;
        float: right;
        border: #e5f9fe solid 1px;
        >div{
            float: right;
            width: 100%;
            height: 100px;
            overflow: hidden;
        }
    }
    >div:hover{
        background: #e6f1ff;
    }
    .expectationTwo_p{
        font-size: 1.1rem;
        color: #ffb400;
    }
}
.expectationThree{
    width: 100%;
    float: left;
    margin-bottom: 15%;
div:nth-child(1){
    width: 10%;
    float: left;
    font-style: italic;
    color: #9299a1;
    margin-bottom: 5%;
    margin-top: 5%;
}
div:nth-child(2){
    width: 60%;
    float: left;
    margin-bottom: 5%;
    margin-top: 5%;
}
div:nth-child(3){
    width: 30%;
    float: right;
    color: #ffb400;
    margin-bottom: 5%;
    margin-top: 5%;
}
>ul li{
    width: 100%;
    float: left;
}
>ul li:hover{
    background: #e6f1ff;
}
}

//TOP
.Top{
    width: 100%;
    >h1{
        text-align: left;
        font-size: 1.6rem;
        color: #ffb400;
        margin-bottom: 5%;
        width: 100%;
        float: left;
        >span{
            float: right;
            >a{
                font-size: 1.2rem;
                color: #ffb400;
            }
            >a:hover{
                border-bottom: 3px solid #ffb400;
            }
        }
    }
    font-size: 1.2rem;
    margin-bottom: 10%;
}
.none{
    clear: both;
}
</style>