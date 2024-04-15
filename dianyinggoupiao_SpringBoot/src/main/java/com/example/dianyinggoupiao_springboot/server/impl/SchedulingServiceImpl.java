package com.example.dianyinggoupiao_springboot.server.impl;

import com.example.dianyinggoupiao_springboot.entity.Cinema;
import com.example.dianyinggoupiao_springboot.entity.CinemaAndSchedulingTable;
import com.example.dianyinggoupiao_springboot.entity.Scheduling;
import com.example.dianyinggoupiao_springboot.repositroy.CAndSTableRepository;
import com.example.dianyinggoupiao_springboot.repositroy.SchedulingRepository;
import com.example.dianyinggoupiao_springboot.server.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    @Autowired
    SchedulingRepository schedulingRepository;
    @Autowired
    private CAndSTableRepository cAndSTableRepository;

    @Override
    public List<Scheduling> findSchedulingAll() {
        return schedulingRepository.findAll();
    }

    @Override
    public Optional<Scheduling> findById(Integer id) {
        return schedulingRepository.findById(id);
    }

    @Override
    public boolean saveScheduling(Scheduling scheduling) {
        try {
            schedulingRepository.save(scheduling);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    /**
     * 根据排片名称获取所有影院对应的排片id
     * @param scheduling_name
     * @return 包含该影片排片的 排片id
     */
    @Override
    public List<Integer> findIdByName(String scheduling_name) {
        List<Scheduling> list = schedulingRepository.findByName(scheduling_name);
        List<Integer> ids = new ArrayList<>();
        for (Scheduling scheduling : list) {
            ids.add(scheduling.getId());
        }
        return ids;
    }

    /**
     * 根据排片id获取不重复的影院信息
     * @param ids 排片id
     * @return List<Cinema>
     */
    @Override
    public List<Cinema> findCinemaByIds(List<Integer> ids) {
        List<CinemaAndSchedulingTable> bySchedulingData_idIn = cAndSTableRepository.findBySchedulingData_IdIn(ids);
        List<Cinema> cinemaList=new ArrayList<>();
        for (CinemaAndSchedulingTable cinemaAndSchedulingTable : bySchedulingData_idIn) {
            cinemaList.add(cinemaAndSchedulingTable.getCinemaData());
        }
        // 使用HashSet去除重复元素
        Set<Cinema> setIds = new HashSet<>(cinemaList);

        // 清空原始List
        cinemaList.clear();

        // 将去除重复后的元素重新放入原始List中
        cinemaList.addAll(setIds);
        return cinemaList;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            schedulingRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
