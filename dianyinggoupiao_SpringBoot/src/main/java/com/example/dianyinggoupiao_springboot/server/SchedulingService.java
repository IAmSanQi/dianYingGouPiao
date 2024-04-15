package com.example.dianyinggoupiao_springboot.server;


import com.example.dianyinggoupiao_springboot.entity.Cinema;
import com.example.dianyinggoupiao_springboot.entity.Scheduling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SchedulingService {
    List<Scheduling> findSchedulingAll();
    Optional<Scheduling> findById(Integer id);

    boolean saveScheduling(Scheduling scheduling);

    List<Integer> findIdByName(String scheduling_name);
    List<Cinema> findCinemaByIds(List<Integer> ids);

    boolean deleteById(int id);
}
