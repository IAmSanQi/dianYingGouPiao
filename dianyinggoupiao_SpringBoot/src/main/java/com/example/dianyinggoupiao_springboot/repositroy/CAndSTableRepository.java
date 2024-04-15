package com.example.dianyinggoupiao_springboot.repositroy;

import com.example.dianyinggoupiao_springboot.entity.CinemaAndSchedulingTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CAndSTableRepository extends JpaRepository<CinemaAndSchedulingTable,Integer> {
    /**
     * 根据cinema_id查询多条排片信息
     * @param cinema_id 影院id
     * @return List<CinemaAndSchedulingTable >
     */
    List<CinemaAndSchedulingTable> findByCinemaData_CinemaId(int cinema_id);
    /**
     * 根据 scheduling_id 查询排片信息
     * @param scheduling_id 影院id
     * @return CinemaAndSchedulingTable
     */
    CinemaAndSchedulingTable findBySchedulingData_Id(int scheduling_id);

    /**
     * 根据影院 id 获取 pageCinemaAndSchedulingTable 数据
     * @param cinema_id 影院id
     * @param pageable 分页数据
     * @return CinemaAndSchedulingTable 数据
     */
    Page<CinemaAndSchedulingTable>findByCinemaData_CinemaId(int cinema_id, Pageable pageable);

    /**
     * 根据Scheduling_Id删除数据
     * @param id Scheduling_Id
     */
    void deleteBySchedulingData_Id(int id);

    /**
     * 根据 SchedulingData_Id 查找 CinemaAndSchedulingTable
     * @param schedulingId scheduling的Id
     * @return CinemaAndSchedulingTable
     */
    List<CinemaAndSchedulingTable> findBySchedulingData_IdIn(List<Integer> schedulingId);


}
