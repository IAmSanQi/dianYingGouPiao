package com.example.dianyinggoupiao_springboot.server;

import com.example.dianyinggoupiao_springboot.entity.CinemaAndSchedulingTable;
import com.example.dianyinggoupiao_springboot.entity.Scheduling;

import java.util.List;

public interface CAndSTableService {
    List<CinemaAndSchedulingTable> findAll();

    void save(CinemaAndSchedulingTable cinemaAndSchedulingTable);
    List<CinemaAndSchedulingTable> findAllById(List<Integer> id);

    List<Scheduling> findSchedulingByCinema_id(int cinema_id);

    Scheduling findSchedulingByScheduling_id(int scheduling_id);

    boolean deleteBySchedulingData_Id(int id);

    boolean delManyScheduling(List<Integer> schedulingIdList);
}
