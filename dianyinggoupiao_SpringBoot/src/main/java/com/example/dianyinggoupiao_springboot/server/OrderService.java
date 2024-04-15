package com.example.dianyinggoupiao_springboot.server;

import com.example.dianyinggoupiao_springboot.entity.Order;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderService {

    public List<Order> findOrderAll();

    public List<Order> findOrderByName(String user_id);
    public void saveOrder(Order order);

    public void updateOrder(String order_id);

    public void delOrder(String order_id);

    public void emptyOrder(String user_id);
    public Order findOrder_id(String order_id);

    public List<Order> findByCinemaName(String cinemaName);


}
