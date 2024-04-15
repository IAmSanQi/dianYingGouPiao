package com.example.dianyinggoupiao_springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dianyinggoupiao_springboot.entity.Order;
import com.example.dianyinggoupiao_springboot.server.CinemaAccService;
import com.example.dianyinggoupiao_springboot.server.CinemaService;
import com.example.dianyinggoupiao_springboot.server.OrderService;
import com.example.dianyinggoupiao_springboot.util.ALiPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/Order")
public class OrderController {
    private OrderService orderService;
    private ALiPayUtil aLiPayUtil;
    @Autowired
    private CinemaAccService cinemaAccService;
    @Autowired
    private CinemaService cinemaService;
    private String order_id="";

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setALiPayUtil(ALiPayUtil aLiPayUtil) {
        this.aLiPayUtil = aLiPayUtil;
    }

    /**
     * 随机生成订单号
     *
     * @return 一个随机的订单号
     */
    @GetMapping("/generatedOrderId")
    public String generatedOrderId() {
        // 获取当前的年月日时分秒
        LocalDateTime currentTime = LocalDateTime.now();

        // 提取年月日时分秒中的数字 "年"
        int year = currentTime.getYear();

        // 组成字符串
        String result = String.valueOf(year);

        boolean flag = true;
        while (flag) {
            if (result.length() < 17) {
                Random random = new Random();

                int num1 = random.nextInt(1000); // 生成0到999之间的随机数
                int num2 = random.nextInt(1000);
                result = result + String.valueOf((num1 * num2) % 10);
            } else if (result.length() == 17) {
                flag = false;
            }
        }
        // 输出结果
        System.out.println("提取的数字组成的字符串: " + result);
        return result;
    }

    @GetMapping("/findOrderAll")
    public List<Order> findOrderAll() {
        return orderService.findOrderAll();
    }

    /**
     * 根据用户账号查询订单信息
     *
     * @param request body
     * @return 订单信息
     */
    @PostMapping("/findOrderByName")
    public List<Order> findOrderByName(@RequestBody Map<String, String> request) {
        String user_id = request.get("order");
        return orderService.findOrderByName(user_id);
    }


    /**
     * 保存新的订单信息
     *
     * @param request 订单信息
     */
    @PostMapping("/saveOrder")
    public void saveOrder(@RequestBody Map<String, String> request) {
        String data = request.get("order");
        JSONObject jsonObject = JSON.parseObject(data);
        String user_id = jsonObject.getString("user_id");
        String order_id = jsonObject.getString("order_id");
        String movieName = jsonObject.getString("movieName");
        String cinemaName = jsonObject.getString("cinemaName");
        String cinemaHall = jsonObject.getString("cinemaHall");
        String seat = jsonObject.getString("seat");
        String date = jsonObject.getString("date");
        String money = jsonObject.getString("money");
        String type = jsonObject.getString("type");


        Order order = new Order();

        order.setUser_id(user_id);
        order.setOrder_id(order_id);

        order.setMovieName(movieName);
        order.setCinemaName(cinemaName);
        order.setCinemaHall(cinemaHall);

        order.setSeat(seat);
        order.setDate(date);
        order.setMoney(money);
        order.setType(type);

        orderService.saveOrder(order);
    }

    /**
     * 删除订单信息
     *
     * @param request 订单信息
     */
    @PostMapping("/delOrder")
    public void delOrder(@RequestBody Map<String,String> request) {
        String order_id=request.get("order");
        if (orderService.findOrder_id(order_id)!=null){
            orderService.delOrder(order_id);
        }
    }
    /**
     * 清空订单信息
     *
     * @param request 订单信息
     */
    @PostMapping("/emptyOrder")
    public void emptyOrder(@RequestBody Map<String,String> request) {
        String user_id=request.get("order");
        if (orderService.findOrderByName(user_id)!=null){
            orderService.emptyOrder(user_id);
        }
    }
    /**
     * 创建支付宝支付页面
     *
     * @param request 前端传入的商品信息数据
     * @return 跳转页面
     */
    @PostMapping("/create")
    public String create(@RequestBody Map<String, String> request) {
        String data = request.get("order");
        JSONObject jsonObject = JSON.parseObject(data);
        String id = jsonObject.getString("order_id");
        order_id=id;
        String price = jsonObject.getString("price");
        String title = jsonObject.getString("movieName");
        String str1 = aLiPayUtil.pay(id, price, title);
        String str2 = "<script>document.forms[0].submit();</script>";
        return str1.replace(str2, "");
    }

    /**
     * 查询订单是否交易成功
     *
     */
    @GetMapping("/returnQuery")
    public String returnQuery() {
        String str = aLiPayUtil.query(order_id, "");
        String success = "\"trade_status\":\"TRADE_SUCCESS\"";
        int index = str.indexOf(success);
        if (index != -1){
            //修改订单状态
            orderService.updateOrder(order_id);
            return "交易成功";
        }
        return "交易失败";
    }

    /**
     * 根据影院账户查找对应的所有订单
     * @param request 影院账户
     * @return 订单数据
     */
    @PostMapping("/findByCinemaName")
    public List<Order> findByCinemaName(@RequestBody String request){
        JSONObject jsonObject = JSON.parseObject(request);
        String accName = jsonObject.getString("data");
        accName=accName.replace("\"","");
        int idAcc = cinemaAccService.findCinemaId(accName);
        String name ="";
        if (cinemaService.findById(idAcc).orElse(null)==null){
            return null;
        }else {
           name=cinemaService.findById(idAcc).orElse(null).getName();
        }

        return orderService.findByCinemaName(name);
    }
}
