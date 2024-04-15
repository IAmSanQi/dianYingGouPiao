<template>
    <div>
        <div class="heard">
            <el-steps :space="750" :active="2" finish-status="success" class="steps" align-center>
                <el-step title="选择影片场次" />
                <el-step title="选择座位" />
                <el-step title="14分钟内付款" />
                <el-step title="影院取票观影" />
            </el-steps>
        </div>
        <div class="main">
            <div class="tips">
                <p v-if="countdown(sOrder.order_id)>0">请在 <span>{{getFormattedCountdown(sOrder.order_id)}}</span>内完成支付</p>
                <p v-if="countdown(sOrder.order_id)<=0">订单超时{{removeOrder(sOrder.order_id)}}</p>
                <p>超时订单会自动取消，如遇支付问题，请致电三柒客服：0000-1111</p>
            </div>
            <div class="pay">
                <el-table :data="tableData" style="width: 100%;margin-top: 40px">
                    <el-table-column prop="movieName" label="影片" />
                    <el-table-column prop="date" label="日期"  />
                    <el-table-column prop="startTime" label="开始时间"  />
                    <el-table-column prop="endTime" label="结束时间"  />
                    <el-table-column prop="cinema" label="影院"  width="240"/>
                    <el-table-column prop="movieHall" label="影厅" />
                    <el-table-column prop="seat" label="座位" />
                </el-table>
                <div>
                    <p>实际支付 : <span>￥{{amount.value}}</span></p>
                    <el-button round="round"  @click="goto">确认支付</el-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import router from '@/router'
import {useRoute} from 'vue-router'
import {onBeforeMount, onMounted, reactive, ref} from 'vue'
import {getOrder, postOrder, setSelectHttpName} from '@/http/orderApi'
import storeOrder from '@/store/storeOrder'
export default {
    name: "payment",
    setup(){
        let route=useRoute();
        let seat=route.query.ticket
        let movieData=ref()
        let cinemaData=ref({})
        let buyData=reactive({})
        let amount=reactive({})
        let day=reactive({})
        // 从 localStorage 中获取值
        const globalVariable = localStorage.getItem('globalVariable');

        let tableData=reactive([
                {
                    movieName:null,
                    date:null,
                    startTime:null,
                    endTime:null,
                    cinema:null,
                    movieHall:null,
                    seat: [],
                }
            ])
        let order=ref({
            user_id:null,
            order_id:null,
            movieName:null,
            cinemaName:null,
            cinemaHall:null,
            seat:"",
            date:null,
            money:null,
            type:1,
        })
        const sOrder =ref({
            order_id:"",
            countdown:15*60,
        })
        let payInfo=reactive({
            order_id:"",
            price:"",
            movieName:""
        })
        //Object.assign将源对象的属性和值合并到目标对象中
        movieData.value=JSON.parse( route.query.movieDate)
        cinemaData.value=JSON.parse(route.query.cinemaData)
        Object.assign(buyData,JSON.parse(route.query.buyData))
        Object.assign(amount,JSON.parse(route.query.amount))
        Object.assign(day,JSON.parse(route.query.day))

        console.log("cinemaData.value:",cinemaData.value)
        tableData[0].movieName=movieData.value.name
        tableData[0].date=day.value
        tableData[0].startTime=cinemaData.value.startTime
        tableData[0].endTime=cinemaData.value.endTime
        tableData[0].cinema=buyData.name
        tableData[0].movieHall=cinemaData.value.movieTheater
        tableData[0].seat=seat

        let saveOrder=()=>{
            order.value.user_id=globalVariable
            //获取，随机生成的订单号
            setSelectHttpName("http://localhost:8181/Order/generatedOrderId")
            getOrder().then(async (result)=>{
                order.value.order_id=result;

                payInfo.order_id=order.value.order_id;
                sOrder.value.order_id=order.value.order_id;
                await startCountdownForOrder(sOrder.value.order_id);

                order.value.movieName=movieData.value.name
                order.value.cinemaName=buyData.name
                order.value.cinemaHall=cinemaData.value.movieTheater
                seat.forEach((item)=>{
                    if (item!==null){
                        order.value.seat+=item
                    }
                })
                order.value.date=day.value+" "+cinemaData.value.startTime
                order.value.money=amount.value

                setSelectHttpName("http://localhost:8181/Order/saveOrder")
                await postOrder(JSON.stringify(order.value))
            }).catch(error => {
                console.error("请求失败：", error);
                console.error("错误信息：", error.response.data);
            });
        }



        let goto=()=>{
            payInfo.price=amount.value;
            payInfo.movieName=movieData.value.name;

            console.log(payInfo)
            setSelectHttpName("http://localhost:8181/Order/create")
            postOrder(JSON.stringify(payInfo)).then((result)=>{
                router.push({name:"aLiPayView",query:{form:result}})
            })
        }

        //----------------------------------定时器------------------------------------------
        storeOrder.commit('addOrder', sOrder.value);
        console.log("sOrder.value:",sOrder.value)
        console.log("orders:",storeOrder.state.orders)
        // 启动倒计时，并存储 intervalId
        const startCountdownForOrder = (orderId) => {
            return storeOrder.dispatch('startCountdown', orderId);
        };
        //获取格式数据
        const getFormattedCountdown = (orderId)=>{
            return storeOrder.getters.getFormattedCountdown(orderId)
        }

        const countdown = (orderId)=>{
            return storeOrder.getters.getOrderById(orderId).countdown;
        }
        //删除id为orderId的定时器
        const removeOrder= (orderId) =>{
            return storeOrder.commit('storeOrder',orderId)
        }

        onBeforeMount(()=>{
            saveOrder();
        })
        return{
            tableData,
            amount,
            getFormattedCountdown,
            sOrder,
            countdown,
            goto,
            saveOrder,
            removeOrder,
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
}
.tips{
    width: 100%;
    float: left;
    padding: 20px;
    background: rgb(255,243,243);
    >p:nth-child(1){
        width: 100%;
        float: left;
        margin-left: 40px;
        font-size: 1.2rem;
        >span{
            font-size: 1.8rem;
            color: #f34d41;
        }
    }
    >p:nth-child(2){
        width: 100%;
        float: left;
        margin-left: 40px;
        color: #f34d41;
        margin-top: 10px;
    }
}
.pay{
    width: 100%;
    float: left;
    >div{
        float: right;
        >p{
            margin-right: 20px;
            margin-top: 40px;
            >span{
                color: #f34d41;
                font-size: 1.8rem;
            }
        }
        >button{
            margin-top: 20px;
            background: #f34d41;
            color: white;
        }
    }
}
</style>