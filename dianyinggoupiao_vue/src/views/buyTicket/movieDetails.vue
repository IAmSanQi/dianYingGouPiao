<template>
    <div>
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
                        <a href="#" @click="movieTicket(movieInfoDate.name)"><p>特惠购票</p></a>
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
        <div class="main">
            <div class="introduce">
                <p>介绍</p>
                <p>剧情简介</p>
                <p v-if="movieInfoDate!=null">
                    {{movieInfoDate.movie_info.introduce}}
                </p>
            </div>
        </div>
    </div>
</template>

<script>
import {useRoute} from 'vue-router'
import {reactive, ref,} from 'vue'
import {findMovieInfo, getMovieInfo, setSelectHttpName, getImg, findMovieInfoByName, getImgUrl} from '@/http/movieApi'
import router from '@/router'
export default {
    name: "movieDetails",
    setup(){
        let route=useRoute();
        let movieName="";
        const movieInfoDate=ref();
        const imageUrl=ref()
        movieName=route.query.movieDetailsData;
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
            router.push({name: "ticketing", query: {ticketing: name}})
        }
        let back=()=>{
            router.go(-1);
        }
        return{
            movieInfoDate,
            movieName,
            imageUrl,
            getImg,
            movieTicket,
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
.main{
    width: 80%;
    float: left;
    margin-top: 1%;
    margin-left: 10%;
}
.introduce{
    float: left;
    width: 60%;
    >p{
        margin-top: 1%;
    }
    >p:nth-child(1){
        font-size: 1.8rem;
        border-bottom: #9299a1 solid 2px;
    }
}
.jq{

}
</style>