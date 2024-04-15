<template>
    <div>
        <div class="main">
            <div class="contents" v-for="(item,index) in movieData.value" v-if="movieData.value!=null" @click="goToBuyTicket(index)">
                <div class="contents_left">
                    <a><img :src="getImg(item.name)"></a>
                </div>
                <div class="contents_right">
                    <p>{{item.name}}</p>
                    <p>{{item.score}}</p>
                    <p>{{item.movie_info.type}}</p>
                    <p>{{item.movie_info.timeString}}中国大陆上映</p>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
import {useRoute} from 'vue-router'
import {reactive, watchEffect} from 'vue'
import {getSearchMovieData, searchMovie, setSelectHttpName,getImg} from '@/http/movieApi'
import router from '@/router'

export default {
    name: "contentView",
    setup(){
        const route=useRoute()
        let searchData=reactive({
            input:""
        })
        searchData.input=route.query.searchData;
        let movieData=reactive([])
        setSelectHttpName("http://localhost:8181/Movie/findByMovieNameInfo")
        searchMovie(searchData.input)
        setTimeout(()=>{
            movieData.value=getSearchMovieData()
        },1000)
        function goToBuyTicket(index){
            //不能删除，用于将去掉 ""
            let movieName=movieData.value[index].name;
            router.push({name:"movieDetails",query:{movieDetailsData: movieName}})
        }
        // watchEffect(()=>{
        //         setTimeout(()=>{
        //         movieData=getSearchMovieData()
        //         console.log("movieData:")
        //         console.log(movieData)
        //         router.push({path:"/MovieContent"})
        //     },1000)
        // })

        return{
            searchData,
            movieData,
            getImg,
            goToBuyTicket
        }
    }
}
</script>

<style lang="scss" scoped>
.main{
    width: 100%;
    float: left;
}
.contents{
    width: 29%;
    float: left;
    margin-left: 10%;
    margin-right: 10%;
    margin-top: 2%;
}
.contents_left{
    width: 36%;
    float: left;
}
.contents_right{
    width: 58%;
    float: left;
    margin-left: 6%;
    height: 100%;
    border-bottom: 2px solid rgb(200,200,200);
    >p:nth-child(1){
        margin-top: 10%;
        font-size: 1.8rem;

    }
    >p:nth-child(2){
        font-size: 1.6rem;
        margin-top: 10%;
        color: #ffb400;
    }
    >p:nth-child(3){
        margin-top: 5%;
        font-size: 1.2rem;
        font-family: "华文仿宋";
        font-weight: bold;
    }
    >p:nth-child(4){
        font-size: 1.1rem;
        margin-top: 2%;
        color: rgb(153,153,153);
        margin-bottom: 10%;
    }

}
</style>