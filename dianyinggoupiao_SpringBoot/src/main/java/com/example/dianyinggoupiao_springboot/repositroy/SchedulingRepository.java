package com.example.dianyinggoupiao_springboot.repositroy;

import com.example.dianyinggoupiao_springboot.entity.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SchedulingRepository extends JpaRepository<Scheduling,Integer> {
    @Override
    Optional<Scheduling> findById(Integer id);

    List<Scheduling> findByName(String scheduling_name);

}
