package com.example.dianyinggoupiao_springboot.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("resultMap")//让spring创建此类对象 对象名：resultMap
public class ResultMap<T> {
    private List<T> list=new ArrayList<T>();


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
