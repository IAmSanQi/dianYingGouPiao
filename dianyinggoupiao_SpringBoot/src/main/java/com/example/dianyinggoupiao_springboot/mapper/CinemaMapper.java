package com.example.dianyinggoupiao_springboot.mapper;

import com.example.dianyinggoupiao_springboot.entity.Cinema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 已经被CinemaRepository 接口替代
 */
@Mapper
@Repository
public interface CinemaMapper {

    @Select("select * from cinema")
    public List<Cinema> findCinemaAll();
}
