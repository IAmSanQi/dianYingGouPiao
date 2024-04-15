package com.example.dianyinggoupiao_springboot.server.impl;

import com.example.dianyinggoupiao_springboot.entity.Order;
import com.example.dianyinggoupiao_springboot.mapper.OrderMapper;
import com.example.dianyinggoupiao_springboot.server.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper){
        this.orderMapper=orderMapper;
    }
    @Override
    public List<Order> findOrderAll() {
        return orderMapper.findOrderAll();
    }
    @Override
    public List<Order> findOrderByName(String user_id) {
        return orderMapper.findOrderByName(user_id);
    }
    @Override
    public void saveOrder(Order order) {
        orderMapper.addOrder(order);
    }
    @Override
    public void updateOrder(String order_id) {
        orderMapper.updateOrder(order_id);
    }

    @Override
    public void delOrder(String order_id) {
        orderMapper.delOrder(order_id);
    }

    @Override
    public void emptyOrder(String user_id) {
        orderMapper.emptyOrder(user_id);
    }

    @Override
    public Order findOrder_id(String order_id) {
        return orderMapper.findOrder_id(order_id);
    }

    @Override
    public List<Order> findByCinemaName(String cinemaName) {
        try {
            return orderMapper.findByCinemaName(cinemaName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
