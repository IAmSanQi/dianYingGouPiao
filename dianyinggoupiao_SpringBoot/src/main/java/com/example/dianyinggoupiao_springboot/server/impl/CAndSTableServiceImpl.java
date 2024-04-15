package com.example.dianyinggoupiao_springboot.server.impl;

import com.example.dianyinggoupiao_springboot.entity.CinemaAndSchedulingTable;
import com.example.dianyinggoupiao_springboot.entity.Scheduling;
import com.example.dianyinggoupiao_springboot.repositroy.CAndSTableRepository;
import com.example.dianyinggoupiao_springboot.repositroy.SchedulingRepository;
import com.example.dianyinggoupiao_springboot.server.CAndSTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class CAndSTableServiceImpl implements CAndSTableService {
    @Autowired
    private CAndSTableRepository cAndSTableRepository;
    @Autowired
    private SchedulingRepository schedulingRepository;

    @Override
    public List<CinemaAndSchedulingTable> findAll() {
        return cAndSTableRepository.findAll();
    }

    @Override
    public void save(CinemaAndSchedulingTable cinemaAndSchedulingTable) {
        cAndSTableRepository.save(cinemaAndSchedulingTable);
    }

    @Override
    public List<CinemaAndSchedulingTable> findAllById(List<Integer> id) {
        return cAndSTableRepository.findAllById(id);
    }

    /**
     * 根据cinema_id查询多条排片信息
     * @param cinema_id 影院id
     * @return List<CinemaAndSchedulingTable >
     */
    @Override
    public List<Scheduling> findSchedulingByCinema_id(int cinema_id){
        List<CinemaAndSchedulingTable> byCinemaData_cinemaId = cAndSTableRepository.findByCinemaData_CinemaId(cinema_id);
        List<Scheduling> schedulings= new ArrayList<>();
        for (CinemaAndSchedulingTable cinemaAndSchedulingTable : byCinemaData_cinemaId) {
            schedulings.add(cinemaAndSchedulingTable.getSchedulingData());
        }

        return schedulings;
    }
    /**
     * 根据 scheduling_id 查询排片信息
     * @param scheduling_id 影院id
     * @return CinemaAndSchedulingTable
     */
    @Override
    public Scheduling findSchedulingByScheduling_id(int scheduling_id){
        return cAndSTableRepository.findBySchedulingData_Id(scheduling_id).getSchedulingData();
    }

    @Override
    @Transactional
    public boolean deleteBySchedulingData_Id(int id) {

        try {
            cAndSTableRepository.deleteBySchedulingData_Id(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delManyScheduling(List<Integer> schedulingIdList) {
        List<Integer> idList= new ArrayList<>();
        try {
            //获取对应的CinemaAndSchedulingTable
            List<CinemaAndSchedulingTable> cList = cAndSTableRepository.findBySchedulingData_IdIn(schedulingIdList);
            for (CinemaAndSchedulingTable cinemaAndSchedulingTable : cList) {
                idList.add(cinemaAndSchedulingTable.getId());
            }
            cAndSTableRepository.deleteAllByIdInBatch(idList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        schedulingRepository.deleteAllByIdInBatch(idList);
        return true;
    }
}
