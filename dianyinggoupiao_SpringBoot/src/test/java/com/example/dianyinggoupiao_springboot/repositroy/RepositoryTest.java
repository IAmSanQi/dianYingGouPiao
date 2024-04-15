package com.example.dianyinggoupiao_springboot.repositroy;

import com.example.dianyinggoupiao_springboot.entity.Cinema;
import com.example.dianyinggoupiao_springboot.entity.CinemaAndSchedulingTable;
import com.example.dianyinggoupiao_springboot.entity.Scheduling;
import com.example.dianyinggoupiao_springboot.server.CAndSTableService;
import com.example.dianyinggoupiao_springboot.server.CinemaAccService;
import com.example.dianyinggoupiao_springboot.server.impl.SchedulingServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@SpringBootTest
@Transactional
class RepositoryTest {

    @Autowired
    private SchedulingServiceImpl schedulingService;

    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private CAndSTableRepository cAndSTableRepository;

    @Autowired
    private CinemaAccService cinemaAccService;

    @Autowired
    private CAndSTableService cAndSTableService;

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Test
    void test001(){
//        System.out.println(schedulingService.findSchedulingAll());
        CinemaAndSchedulingTable CS = new CinemaAndSchedulingTable();
        Cinema cinema = cinemaRepository.findById(1).orElse(null);
        Scheduling scheduling = schedulingService.findById(1).orElse(null);

        CS.setCinemaData(cinema);
        CS.setSchedulingData(scheduling);
        cAndSTableRepository.save(CS);
        System.out.println(cAndSTableRepository.findAll());
    }

    /**
     * 根据cinemaName获取Scheduling数据数据
     */
    @Test
    void test002(){
        Optional<Cinema> byId = cinemaRepository.findById(1);
        System.out.println(byId);
        Optional<Cinema> cinema = cinemaRepository.findByName("SFC上影国际影城（吴江汾湖店）");
        if (cinema.isPresent()) {
            System.out.println(cinema.get().getCinemaId());
            List<CinemaAndSchedulingTable > schedulingList = cAndSTableRepository.findByCinemaData_CinemaId(cinema.get().getCinemaId());
            for (CinemaAndSchedulingTable cas : schedulingList) {
                System.out.println("CinemaData: "+ cas.getCinemaData());
                System.out.println("SchedulingData: "+ cas.getSchedulingData());
            }
            System.out.println("SchedulingData: "+cAndSTableRepository.findBySchedulingData_Id(1).getSchedulingData());
        } else {
            System.out.println("影院不存在");
        }

    }
    /**
     * 根据cinemaName获取Scheduling分页数据数据
     */
    @Test
    void test003(){
        int cinema_id=cinemaAccService.findCinemaId("cinema1");
        Pageable pageable= PageRequest.of(0,1);
        Page<CinemaAndSchedulingTable> byCinemaDataContaining = cAndSTableRepository.findByCinemaData_CinemaId(cinema_id, pageable);
        List<Scheduling> schedulingList= new ArrayList<>();
        for (CinemaAndSchedulingTable cinemaAndSchedulingTable : byCinemaDataContaining) {
            schedulingList.add(cinemaAndSchedulingTable.getSchedulingData());
            System.out.println(schedulingList.get(0));
        }
    }

    /**
     * 保存CinemaAndSchedulingTable数据
     */
    @Test
    void test004(){
        CinemaAndSchedulingTable cinemaAndSchedulingTable = new CinemaAndSchedulingTable();
        Scheduling scheduling = new Scheduling();
        int cinema_id = cinemaAccService.findCinemaId("cinema1");
        System.out.println(cinema_id);
        Optional<Cinema> cinema1 = cinemaRepository.findById(cinema_id);
        System.out.println("cinema1:"+cinema1.orElse(null));
        scheduling.setDate("2025/5/5");
        scheduling.setStartTime("8:20");
        scheduling.setEndTime("10:20");
        scheduling.setName("你的名字");
        scheduling.setType("国语2D");
        scheduling.setMovieTheater("1号影厅");
        BigDecimal price = new BigDecimal("38.55");
        scheduling.setPrice(price);


        cinemaAndSchedulingTable.setSchedulingData(scheduling);
        cinemaAndSchedulingTable.setCinemaData(cinema1.orElse(null));
        System.out.println(cAndSTableRepository.save(cinemaAndSchedulingTable));
    }
    @Test
    @Transactional
    void test005(){
        try {
            List<Scheduling> schedulings = cAndSTableService.findSchedulingByCinema_id(1);
            List<Scheduling> schedulingList= new ArrayList<>();
            for (Scheduling scheduling : schedulings) {
                if (scheduling.getName().equals("你的名字")){
                    schedulingList.add(scheduling);
                }
            }
            System.out.println(schedulingList);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    void test006(){
        try {
            System.out.println("================================================================================\n");

            List<Integer> ids = schedulingService.findIdByName("声之形");

            List<Cinema> cinemaList = schedulingService.findCinemaByIds(ids);
            System.out.println("77777:"+"\n"+cinemaList);

            System.out.println("\n================================================================================");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}