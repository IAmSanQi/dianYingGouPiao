<template>
    <div>
        <div>
            <el-button type="text"   icon="el-icon-arrow-left" style="color: black"  @click="back">返回</el-button>
        </div>
        <div class="heard">
            <el-steps :space="750" :active="1" finish-status="success" class="steps" align-center>
                <el-step title="选择影片场次" />
                <el-step title="选择座位" />
                <el-step title="14分钟内付款" />
                <el-step title="影院取票观影" />
            </el-steps>
        </div>
        <div class="main">
            <div class="main_left">
                <div class="seat_top">
                    <img src="../../assets/可选座位.png">
                    <p>可选座位</p>
                    <img src="../../assets/已售座位.png">
                    <p>已售座位</p>
                    <img src="../../assets/已选座位.png">
                    <p>已选座位</p>
                </div>
                <div class="seat_main">
                    <div class="screen">银幕中央</div>
                    <div class="seats-wrapper" id="seatDiv">
                        <div v-for="item1 in seatData.length" id="nightDiv">
                            <p>{{item1}}</p>
                            <div v-for="(item,index) in seatData[item1-1]" id="div">
                                <div @click="changeSeatImage((item1-1)*17+index,item,item1)" style="cursor: pointer" class="changeDivImage1">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="main_right">
                <div class="movieInfo">
                    <img :src="getImg(movieInfoDate.name)">
                    <h3>{{ movieInfoDate.name }}</h3>
                    <p>类型 : <span>{{ movieInfoDate.movie_info.type }}</span></p>
                    <p>时长 : <span>{{ movieInfoDate.movie_info.play_time }}分钟</span></p>
                </div>
                <div class="cinemaInfo">
                    <p>影院 : <span>{{ buyData.name }}</span></p>
                    <p>影厅 : <span>{{ cinemaData.movieTheater }}</span></p>
                    <p>版本 : <span>{{ cinemaData.type }}</span></p>
                    <p>场次 :
                        <span>
                            {{day.value }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            {{cinemaData.startTime}}-{{cinemaData.endTime}}
                        </span>
                    </p>
                    <p>票价 : <span>￥{{cinemaData.price}}/张</span></p>
                </div>
                <div class="money">
                    <div>
                        <p>座位：一次最多选6个座位</p>
                        <span>请点击左侧座位图选择座位</span>
                        <p></p>
                    </div>
                    <p>总价 :<span>￥{{ amount.value }}</span></p>
                </div>
                <div class="phone">
                    <p>手机号 : <span>183****5621</span></p>
                    <el-button round="round" @click="goto">
                        确认选座
                    </el-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {useRoute} from 'vue-router'
import {reactive, ref} from 'vue'
import {getImg} from '@/http/movieApi'
import router from '@/router'
import {ElMessage} from 'element-plus'
export default {
    name: "selectSeat",
    setup(){
        const route=useRoute()
        let movieInfoDate=ref()
        let cinemaData=ref()
        let buyData=reactive({})
        let day=reactive({})
        movieInfoDate.value=JSON.parse( route.query.movieInfoDate)
        cinemaData.value=JSON.parse( route.query.cinemaData)
        Object.assign(buyData,JSON.parse(route.query.buyData))
        Object.assign(day,JSON.parse(route.query.day))
        let seatData=reactive([
            ['A1','A2','A3','A4','A5','A6','A7','A8','A9','A10','A11','A12','A13','A14','A15','A16','A17'],
            ['B1','B2','B3','B4','B5','B6','B7','B8','B9','B10','B11','B12','B13','B14','B15','B16','B17'],
            ['C1','C2','C3','C4','C5','C6','C7','C8','C9','C10','C11','C12','C13','C14','C15','C16','C17'],
            ['D1','D2','D3','D4','D5','D6','D7','D8','D9','D10','D11','D12','D13','D14','D15','D16','D17'],
            ['E1','E2','E3','E4','E5','E6','E7','E8','E9','E10','E11','E12','E13','E14','E15','E16','E17'],
            ['F1','F2','F3','F4','F5','F6','F7','F8','F9','F10','F11','F12','F13','F14','F15','F16','F17'],
            ['G1','G2','G3','G4','G5','G6','G7','G8','G9','G10','G11','G12','G13','G14','G15','G16','G17'],
            ['H1','H2','H3','H4','H5','H6','H7','H8','H9','H10','H11','H12','H13','H14','H15','H16','H17'],
            ['I1','I2','I3','I4','I5','I6','I7','I8','I9','I10','I11','I12','I13','I14','I15','I16','I17']
            ])
        let amount=reactive({value:0});
        let count=ref(0);
        let num=ref(0)
        let text=reactive([null,null,null,null,null,null]);
        let changeSeatImage=(i,seatDataValue,pai)=>{
            let seatDiv=document.querySelector('#seatDiv');
            let nightDivs=seatDiv.querySelectorAll('#nightDiv')
            let span=document.querySelector('.money div')
            if(num.value!==i){
                num.value=i
                nightDivs.forEach((item1,index1)=>{
                        item1.querySelectorAll('#div div').forEach((item,index)=>{

                            if (num.value===index1*(item1.querySelectorAll('#div div').length)+index){
                                if (item.classList.contains("changeDivImage1")){
                                    if (count.value<6){
                                        item.classList.remove('changeDivImage1')
                                        item.classList.add('changeDivImage2')
                                        amount.value+=parseFloat(cinemaData.value.price)
                                        if (count.value===0){
                                            span.innerHTML =pai+"排"+seatDataValue+"座"+"\n"
                                            text[count.value]=pai+"排"+seatDataValue+"座"
                                        }else {
                                            span.innerHTML +=pai+"排"+seatDataValue+"座\t"
                                            text[count.value]=pai+"排"+seatDataValue+"座"
                                        }
                                        count.value++;
                                    }
                                }else {
                                    item.classList.remove('changeDivImage2')
                                    item.classList.add('changeDivImage1')
                                    amount.value-=parseFloat(cinemaData.value.price)
                                    let textToRemove = pai + "排" + seatDataValue + "座";
                                    span.innerHTML = span.innerHTML.replace(textToRemove, "");
                                    count.value--;
                                    for (let j = 0; j < text.length; j++) {
                                        if(text[j] && text[j].indexOf(textToRemove) !== -1){
                                            text.splice(j, 1);
                                            break;
                                        }
                                    }
                                }
                            }
                        })
                    })
            }else {
                nightDivs.forEach((item1,index1)=>{
                    item1.querySelectorAll('#div div').forEach((item,index)=>{

                        if (num.value===index1*(item1.querySelectorAll('#div div').length)+index){
                            if (item.classList.contains("changeDivImage2")){
                                item.classList.remove('changeDivImage2')
                                item.classList.add('changeDivImage1')
                                amount.value-=parseFloat(cinemaData.value.price)
                                let textToRemove = pai + "排" + seatDataValue + "座";
                                span.innerHTML = span.innerHTML.replace(textToRemove, "");
                                count.value--;
                                for (let j = 0; j < text.length; j++) {
                                    if(text[j] && text[j].indexOf(textToRemove) !== -1){
                                        text.splice(j, 1);
                                        break;
                                    }

                                }
                            }else {
                                if (count.value<6){
                                    item.classList.remove('changeDivImage1')
                                    item.classList.add('changeDivImage2')
                                    amount.value+=parseFloat(cinemaData.value.price)
                                    if (count.value===0){
                                        span.innerHTML =pai+"排"+seatDataValue+"座"+"\n"
                                        text[count.value]=pai+"排"+seatDataValue+"座"
                                    }else {
                                        span.innerHTML +=pai+"排"+seatDataValue+"座"+"\n"
                                        text[count.value]=pai+"排"+seatDataValue+"座"
                                    }
                                    count.value++;
                                }
                            }
                        }
                    })
                })
            }
        }
        let goto=()=>{
            if(text[0]!==null){
                router.push({
                    name:'payment',
                    query:
                        {
                            ticket:text,
                            movieDate:JSON.stringify(movieInfoDate.value),
                            cinemaData:JSON.stringify(cinemaData.value),
                            buyData:JSON.stringify(buyData),
                            amount:JSON.stringify(amount),
                            day:JSON.stringify(day),
                        }
                })
            }else {
                ElMessage({
                    showClose: true,
                    message: '请选择座位',
                    type: 'error',
                })
            }
        }
        let back=()=>{
            router.go(-1);
        }
        return{
            movieInfoDate,
            cinemaData,
            buyData,
            getImg,
            changeSeatImage,
            amount,
            seatData,
            goto,
            day,
            back
        }
    }
}
</script>

<style scoped lang="scss">
.heard{
    width: 80%;
    float: left;
    margin-left: 10%;
}
.steps{
    float: left;
    width: 100%;
    margin-top: 60px;
}
.main{
    width: 80%;
    float: left;
    margin-left: 10%;
    margin-top:60px ;
    border: rgb(229,229,229) solid 1px;
}
.main_left{
    float: left;
    width: 70%;
}
.seat_top{
    width: 100%;
    float: left;
    margin-left: 120px;
    >p{
        float: left;
        padding: 20px;
    }
    >img{
        float: left;
        padding: 20px;
    }
}
.seat_main{
    float: left;
    width: 100%;
    margin-left: 20px;
}
.screen{
    text-align: center;
    margin-top: 5%;
}

.seats-wrapper{
    width: 100%;
    float: left;
    left: 10%;
    margin-top: 40px;
    >div{
        float: left;
        width: 100%;
        margin-top: 10px;
        >p{
            float: left;
            color: #e5e5e5;
            margin-right: 20px;
        }
        >div{
            float: left;
        }

    }
}
.changeDivImage1{
    float: left;
    background-image: url('@/assets/可选座位.png');
    margin-right: 10px;
    height: 26px;
    width: 30px;
}
.changeDivImage2{
    float: left;
    background-image: url('@/assets/已选座位.png');
    margin-right: 10px;
    height: 26px;
    width: 30px;
}
.changeDivImage3{
    float: left;
    background-image: url('@/assets/已售座位.png');
    margin-right: 10px;
    height: 26px;
    width: 30px;
}

.main_right{
    float: right;
    width: 30%;
    background: rgb(249,249,249);
    font-family: "华文仿宋";
}
.movieInfo{
    width: 100%;
    float: left;
    margin-left: 10px;
    margin-top: 20px;
    >img{
        float: left;
        width: 40%;
    }
    >h3{
        float: left;
        margin-left: 10px;
    }
    >p{
        float: left;
        margin-left: 10px;
        color: rgb(153,153,153);
        margin-top: 10px;
        >span{
            color: black;
        }
    }
}
.cinemaInfo{
    float: left;
    width: 100%;
    margin-left: 10px;
    margin-top: 20px;
    >p{
        color: rgb(153,153,153);
        margin-top: 10px;
    }
}
.money{
    float: left;
    width: 100%;
    margin-left: 10px;
    margin-top: 20px;
    border-bottom: rgb(229,229,229) solid 1px;
    border-top: rgb(229,229,229) solid 1px;
    >div{
        float: left;
        width: 100%;
        >p{
            float: left;
            color: rgb(153,153,153);
            margin-top: 10px;
        }
        >span{
            float: left;
            margin-top: 10px;
            margin-left: 80px;
        }
    }
    >p{
        float: left;
        font-size: 1rem;
        margin-top: 10px;
        >span{
            margin-left: 10px;
            font-size: 1.5rem;
            color: red;
        }
    }
}
.phone{
    float: left;
    width: 100%;
    margin-left: 10px;
    margin-top: 20px;
    >p{
        float: left;
        margin-top: 10px;
        margin-left: 80px;
        >span{
            margin-left: 10px;
            color: red;
        }
    }
    >button{
        float: left;
        margin-left: 100px;
        margin-top: 20px;
        margin-bottom: 20px;
        background: #f34d41;
        color: white;
    }
}


</style>