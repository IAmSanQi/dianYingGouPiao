<template>
    <el-table
        :data="cinemaOrders.items"
        style="width: 100%;"
        stripe
        max-height="480px"
    >
        <el-table-column fixed type="index" width="55"/>
        <el-table-column prop="order_id" label="订单号" width="140"/>
        <el-table-column prop="movieName" label="电影名" width="140"/>
        <el-table-column prop="cinemaHall" label="影厅" width="140"/>
        <el-table-column prop="seat" label="座位" width="140"/>
        <el-table-column prop="date" label="时间" width="140"/>
        <el-table-column prop="money" label="价格" width="140"/>
    </el-table>
</template>

<script>
import {reactive} from 'vue'
import {getOrderTablePost} from '@/util/getTable'

export default {
    name: "cinemaOrderInfo",
    props:{
        cinemaAccName:{
            type: String,
            required: true
        }
    },
    setup(props){
        const cinemaAccName = props.cinemaAccName
        let cinemaOrders=reactive(
            {
                items:[
                    {
                        order_id:"",
                        movieName:"",
                        cinemaHall:"",
                        seat:"",
                        date:"",
                        money:"",
                    }
                ],
            }
        );
        let http="http://localhost:8181/Order/findByCinemaName";
        //获取订单数据
        getOrderTablePost(http,cinemaOrders.items,cinemaAccName).then((res)=>{
           cinemaOrders.items=res;
           cinemaOrders.items.forEach((item)=>{
               item.seat=item.seat.replace(/null/g,"").replace(/座/g, "座，");
           })
       })
        console.log(cinemaOrders.items)
        return {
            cinemaOrders,
        }
    }
}
</script>

<style scoped>

</style>