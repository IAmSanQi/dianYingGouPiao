<template>
    <div>
        <div class="main" v-for="(item,index) in hotScreeningData">
            <ul v-if="index<10">
                <li>
                    <div v-if="index===0" class="main_ranking" style="background: red;color: white">
                        <span>1</span>
                    </div>
                    <div v-if="index===1" class="main_ranking" style="background: #ffb400;color: white">
                        <span>2</span>
                    </div>
                    <div v-if="index===2" class="main_ranking" style="background: #ffb400;color: white">
                        <span>3</span>
                    </div>
                    <div v-if="index>2" class="main_ranking">
                        <span>{{index+1}}</span>
                    </div>
                    <div class="main_img">
                        <el-image
                            :src="item.imageUrl"
                            style="cursor:pointer;min-height: 180px;max-height: 240px; "
                            @click="buyMovie(item.name)"
                        />
                    </div>
                    <div class="main_text">
                        <div>
                            <p>{{item.name}}</p>
                            <p>主演：{{item.movie_info.starring}}</p>
                            <p>上映时间：{{item.movie_info.timeString}}</p>
                        </div>
                        <span>{{item.score}}</span>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import {findInfo, setSelectHttpName, getImg, findMovie} from '@/http/movieApi'
import {reactive, ref} from 'vue'
import router from '@/router'

export default {
    name: "hotScreeningView",
    setup(){
        const hotScreeningData=ref()
        setSelectHttpName("http://localhost:8181/Movie/findBlockbusterInfo");
        findMovie(hotScreeningData)
        function buyMovie(str) {
            router.push({name: "movieDetails", query: {movieDetailsData: str}})
        }
        return{
            hotScreeningData,
            getImg,
            buyMovie
        }
    }
}
</script>

<style scoped lang="scss">
ul,li{
    list-style-type: none;
}

.main{
    width: 70%;
    margin-left: 15%;
    float: left;
    >ul{
        margin-top: 5%;
        float: left;
        width: 100%;
        >li div{
            float: left;
            margin-right: 4%;
        }
    }
}
.main_ranking{
    float: left;
    width: 1%;
    margin-top: 8%;
    padding: 2.3%;
    background: #e5e5e5;
    font-size: 1.2rem;
    font-weight: bold;
    color: #9299a1;
    font-style: italic;
    font-family: "华文仿宋";
}
.main_img{
    float: left;
    width: 20%;
}
.main_text{
    float: left;
    width: 60%;
    border-bottom: #e5e5e5 solid 1px;
    >div{
        float: left;
        width: 80%;
        >p{
            margin-top: 7%;
        }
        :nth-child(1){
            font-size: 1.8rem;
        }
        :nth-child(2){
            font-size: 1.2rem;
        }
        :nth-child(3){
            font-size: 1.2rem;
            color: #9299a1;
            margin-bottom: 6%;
        }
    }
    >span{
        float: right;
        margin-top: 20%;
        color: #ffb400;
        font-size: 2.4rem;
        font-weight: bold;
        font-style: italic;
    }
}
.main_score{
    float: left;
}
</style>