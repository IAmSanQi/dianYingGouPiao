package com.example.dianyinggoupiao_springboot.mapper;

import com.example.dianyinggoupiao_springboot.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    /**
     * 查询order表用所有数据
     * @return List<Order>
     */
    @Select("select * from `order` ")
    public List<Order> findOrderAll();

    /**
     * 查询order表用户的账号为user_id所有数据
     * @param user_id 用户的账号
     * @return 返回order表用户的账号为user_id所以数据
     */
    @Select("select * from `order` where user_id = #{user_id}")
    public List<Order> findOrderByName(String user_id);


    /**
     * 查询order表用户的订单号为order_id的数据
     * @param order_id
     * @return
     */
    @Select("select * from `order` where order_id = #{order_id}")
    public Order findOrder_id(String order_id);

    /**
     * 向order表插入一条信息
     * @param order 插入的账号信息
     */
    @Insert("INSERT INTO `order` (user_id, order_id, movieName, cinemaName, cinemaHall, seat, date, money, type)" +
            "VALUES (#{user_id}, #{order_id}, #{movieName}, #{cinemaName}, #{cinemaHall}, #{seat}, #{date}, #{money}, #{type})")
    public void addOrder(Order order);

    /**
     * 更新订单号为order_id的type值
     * @param order_id 订单号
     */
    @Update("UPDATE `order` SET type=0 WHERE order_id = #{order_id};")
    public void updateOrder(String order_id);

    @Delete("delete from `order` where order_id=#{order_id} ")
    public void delOrder(String order_id);

    @Delete("delete from `order` where user_id=#{user_id} and type=0 ")
    public void emptyOrder(String user_id);

    @Select("select * from `order` where cinemaName=#{cinemaName}")
    public List<Order> findByCinemaName(@Param("cinemaName") String cinemaName);
}
