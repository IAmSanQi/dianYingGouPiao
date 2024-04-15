<template>
    <div v-if="userAccName!==''">
        <el-button round="round" @click="dialogVisible3 = true"  style=" float: right;margin-right: 40px;margin-top: 20px">
            清空已付款的订单
        </el-button>
        <el-dialog
            v-model="dialogVisible3"
            title="提示"
            width="30%"
        >
            <span>你确定要清空以付款的单记录吗？</span>
            <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="dialogVisible3 = false">取消</el-button>
                            <el-button type="primary" @click="emptyOrder()">
                                确定
                            </el-button>
                          </span>
            </template>
        </el-dialog>
        <div class="box" v-for="(item,index) in orderData.value">
            <div class="header">
                <span>2023年{{ item.date}}</span>
                <p>三柒订单号:{{ item.order_id }}</p>
            </div>
            <div class="main" style="position: relative;">
                <img :src="getImg(item.movieName)">
                <div class="text">
                    <h3>《{{ item.movieName }}》</h3>
                    <p>{{item.cinemaName}}</p>
                    <p>{{item.cinemaHall}}<span>{{ item.seat }}</span></p>
                    <p>{{item.date}}</p>
                </div>
                <p>￥{{item.money}}</p>
                <p v-if="item.type==='1'">待支付 &nbsp;&nbsp;&nbsp;&nbsp;
                    <span v-if="countdown(item.order_id)>0" style="color: red">{{getFormattedCountdown(item.order_id)}}</span>
                    <span v-if="countdown(item.order_id)<=0" style="color: red">订单超时{{removeOrder(item.order_id)}}</span>
                </p>
                <p v-if="item.type==='0'">已支付</p>

                <el-button round="round" @click="dialogVisible1 = true" v-if="item.type==='1'" style="position: absolute; bottom: 20px; right: 40px;">
                    取消
                </el-button>
                <el-dialog
                    v-model="dialogVisible1"
                    title="提示"
                    width="30%"
                >
                    <span>你确定要取消这个订单吗？</span>
                    <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="dialogVisible1 = false">取消</el-button>
                            <el-button type="primary" @click="delOrder(index)">
                                确定
                            </el-button>
                          </span>
                    </template>
                </el-dialog>
                <el-button round="round" @click="goto(index)" v-if="item.type==='1'" style="position: absolute;bottom: 20px;right: 140px;">
                    付款
                </el-button>
                <el-button round="round" @click="dialogVisible2 = true" v-if="item.type==='0'">
                    删除订单记录
                </el-button>
                <el-dialog
                    v-model="dialogVisible2"
                    title="提示"
                    width="30%"
                >
                    <span>你确定要删除这个订单记录吗？</span>
                    <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="dialogVisible2 = false">取消</el-button>
                            <el-button type="primary" @click="delOrder(index)">
                                确定
                            </el-button>
                          </span>
                    </template>
                </el-dialog>
            </div>
        </div>
    </div>
    <div v-if="userAccName===''">
        <p>抱歉，您还没有登录</p>
    </div>
</template>

<script>
import {postOrder, setSelectHttpName} from '@/http/orderApi'
import {reactive, ref} from 'vue'
import {getImg} from '@/http/cinemaApi'
import router from '@/router'
import storeOrder from '@/store/storeOrder'

export default {
    name: "order",
    setup(){
        //使用 Web Storage（localStorage 或 sessionStorage）：将值存储在 Web Storage 中，
        // 它们可以在页面刷新时保留数据。您可以使用 localStorage 或 sessionStorage 对象的相关方法来设置和获取值。
        // 从 localStorage 中获取值
        const userAccName = localStorage.getItem('userAccName');
        let dialogVisible1 = ref(false)
        let dialogVisible2 = ref(false)
        let dialogVisible3 = ref(false)
        let orderData=reactive([])
        let payInfo=reactive({
            order_id:"",
            price:"",
            movieName:""
        })
        setSelectHttpName("http://localhost:8181/Order/findOrderByName")

        if (userAccName!==null){
           postOrder(userAccName).then((result)=>{
               orderData.value=result
           }).catch( error =>{
               console.error("错误信息：", error.response.data);
           })
        }
        let goto=(index)=>{
            payInfo.order_id=orderData.value[index].order_id;
            payInfo.price=orderData.value[index].money;
            payInfo.movieName=orderData.value[index].movieName;
            setSelectHttpName("http://localhost:8181/Order/create")
            postOrder(JSON.stringify(payInfo)).then((result)=>{
                router.push({name:"aLiPayView",query:{form:result}})
            })

        }
        let delOrder=(index)=>{
            let order_id=orderData.value[index].order_id
            console.log("index:",index)
            console.log("order_id:",order_id)
            setSelectHttpName("http://localhost:8181/Order/delOrder");
            postOrder(order_id).then(()=>{
                dialogVisible1.value = false
                setSelectHttpName("http://localhost:8181/Order/findOrderByName")
                postOrder(userAccName).then((result)=>{
                    orderData.value=result
                }).catch( error =>{
                    console.error("错误信息：", error.response.data);
                })
            });
        }
        let emptyOrder=()=>{
            let user_id=null
            if (orderData.value.length>0){
                user_id=orderData.value[0].user_id
            }
            setSelectHttpName("http://localhost:8181/Order/emptyOrder")
            postOrder(user_id).then(()=>{
                dialogVisible3.value = false
                setSelectHttpName("http://localhost:8181/Order/findOrderByName")
                postOrder(userAccName).then((result)=>{
                    orderData.value=result
                }).catch( error =>{
                    console.error("错误信息：", error.response.data);
                })
            });
        }

        //----------------------------------定时器------------------------------------------
        //时间
        const countdown  = (orderId)=>{
            if (storeOrder.state.orders.length>0){
                storeOrder.state.orders.forEach(order=>{
                    if (order.order_id===parseInt(orderId)){
                        return storeOrder.getters.getOrderById(parseInt(orderId)).countdown;
                    }
                })
            }
            return 3;
        }
        //获取格式化数据
        const getFormattedCountdown = (orderId)=>{
            return storeOrder.getters.getFormattedCountdown(parseInt(orderId))
        }
        //删除
        const removeOrder= (orderId) =>{
            if (!storeOrder.getters.getOrderById(parseInt(orderId))){
                return "";
            }else if (storeOrder.getters.getOrderById(parseInt(orderId)).countdown===0){
                setSelectHttpName("http://localhost:8181/Order/delOrder");
                postOrder(orderId).then(()=>{
                    dialogVisible1.value = false
                    setSelectHttpName("http://localhost:8181/Order/findOrderByName")
                    postOrder(userAccName).then((result)=>{
                        orderData.value=result
                    }).catch( error =>{
                        console.error("错误信息：", error.response.data);
                    })
                });
            }
            return storeOrder.commit('storeOrder',orderId)
        }
        return{
            userAccName,
            orderData,
            dialogVisible1,
            dialogVisible2,
            dialogVisible3,
            getImg,
            goto,
            delOrder,
            emptyOrder,
            countdown,
            getFormattedCountdown,
            removeOrder,
        }
    }
}
</script>

<style scoped lang="scss">
.box{
    width: 80%;
    float: left;
    margin-left: 10%;
    border: #e5e5e5 solid 1px;
    margin-top: 40px;
}
.header{
    width: 100%;
    float: left;
    background: #e5e5e5;
    >span{
        float: left;
        margin-left: 20px;
        padding: 20px;
    }
    >p{
        float: left;
        margin-left: 40px;
        padding: 20px;
        color: #9299a1;
    }
}
.main{
    float: left;
    padding: 20px;
    width: 100%;
    >img{
        width: 10%;
        float: left;
    }
    .text{
        float: left;
        margin-left: 20px;
        >p{
            margin-top: 10px;
            color: #9299a1;
        }
    }
    >p{
        float: left;
        margin-left: 80px;
        margin-top: 40px;
        font-size: 1.6rem;
    }
    >button{
        float: right;
        margin-right: 80px;
        margin-left: 40px;
        margin-top: 40px;
        background: crimson;
        color: white;
    }
}
</style>