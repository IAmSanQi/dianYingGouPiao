package com.example.dianyinggoupiao_springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dianyinggoupiao_springboot.entity.Cinema;
import com.example.dianyinggoupiao_springboot.repositroy.CinemaRepository;
import com.example.dianyinggoupiao_springboot.server.CinemaAccService;
import com.example.dianyinggoupiao_springboot.server.CinemaService;
import com.example.dianyinggoupiao_springboot.server.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/Cinema")
public class CinemaController {
    @Autowired
    CinemaService cinemaService;

    @Autowired
    CinemaAccService cinemaAccService;

    @Autowired
    private SchedulingService schedulingService;

    public Cinema initCinema(){
        return null;
    }

    @GetMapping("/findCinemaAll")
    public List<Cinema> findCinemaAll() {
        return cinemaService.findCinemaAll();
    }
    @GetMapping("/brandFilter")
    public Set<String> brandFilter() {
        return cinemaService.brandFilter();
    }
    @GetMapping("/administrativeDistrictFilter")
    public  Set<String>administrativeDistrictFilter() {
        return cinemaService.administrativeDistrictFilter();
    }
    @GetMapping("/typeFilter")
    public  Set<String> typeFilter() {
        return cinemaService.typeFilter();
    }

    @PostMapping("/selectCinema")
    public List<Cinema> selectCinema( @RequestBody Map<String,String> request) {
        String data=request.get("data");
        JSONObject jsonObject = JSON.parseObject(data);
        String brand=jsonObject.getString("brandInfo");
        String administrativeDistrict=jsonObject.getString("administrativeDistrictInfo");
        String type=jsonObject.getString("typeInfo");
        String service=jsonObject.getString("serviceInfo");
        return cinemaService.selectCinema(brand,administrativeDistrict,type,service);
    }

    /**
     * 返回当前影片有哪些能正在播放的影院，并且还能根据不同的条件查询
     * @param movie_name 影片名称
     * @param brand 影院品牌
     * @param administrativeDistrict 行政区域
     * @param type 影厅类型
     * @param service 服务
     * @return List<Cinema>
     */
    @PostMapping("/selectCinemaByScheduling")
    public List<Cinema> selectCinemaByScheduling(
            @RequestParam("movie_name") String movie_name,
            @RequestParam("brand") String brand,
            @RequestParam("administrativeDistrict") String administrativeDistrict,
            @RequestParam("type") String type,
            @RequestParam("service") String service
    ) {
        List<Integer> ids = schedulingService.findIdByName(movie_name);
        List<Cinema> cinemaList = schedulingService.findCinemaByIds(ids);

        return cinemaService.selectCinemaByScheduling(cinemaList,brand,administrativeDistrict,type,service);
    }

    @PostMapping("/findCinemaByCinemaAcc_name")
    public Cinema findCinemaByCinemaAcc_name(@RequestParam(name="data") String accName){
        int cinemaId = cinemaAccService.findCinemaId(accName);
        Optional<Cinema> cinema = cinemaService.findById(cinemaId);
        return cinema.orElse(null);
    }

    @PostMapping("/saveOneCinema")
    public boolean saveOneCinema(@RequestBody Cinema cinema){
        if (cinema!=null){
            cinemaService.save(cinema);
            return true;
        }
        return false;
    }

}
