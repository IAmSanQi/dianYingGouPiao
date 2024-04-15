package com.example.dianyinggoupiao_springboot.controller;

import com.example.dianyinggoupiao_springboot.entity.Cinema;
import com.example.dianyinggoupiao_springboot.entity.MovieAllInfo;
import com.example.dianyinggoupiao_springboot.entity.Scheduling;
import com.example.dianyinggoupiao_springboot.repositroy.CinemaRepository;
import com.example.dianyinggoupiao_springboot.server.CinemaAccService;
import com.example.dianyinggoupiao_springboot.server.CinemaAndMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/CinemaAndMovieService")
public class CinemaAndMovieController {
    @Autowired
    private CinemaAndMovieService cinemaAndMovieService;

    @Autowired
    private CinemaAccService cinemaAccService;

    @Autowired
    private CinemaRepository cinemaRepository;

    //----------------------------查询----------------------------------------
    /**
     * 根据影院账号查找当前影院中的所有影片信息
     * @param request 影院的id号
     * @return 某个影院中的所有影片信息
     */
    @PostMapping("/findCinemaAllMovieInfo")
    public List<MovieAllInfo> findCinemaAllMovieInfo(@RequestBody Map<String,String> request) {
        String cinema_name=request.get("data");
        //除去前端的""
        cinema_name=cinema_name.replace("\"","");
        try {
            int cinema_id=cinemaAccService.findCinemaId(cinema_name);
            return cinemaAndMovieService.findCinemaAllMovieInfo(cinema_id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("影院名称为空");
        }
        return null;

    }

    /**
     * 根据影院账号查询影院中所有电影的name
     * @param request 影院名称
     * @return 当前影院中所有电影名称
     */
    @PostMapping("/findNameByMovie")
    public List<String> findNameByMovie(@RequestBody Map<String,String> request){
        String cinema_name=request.get("data");
        //除去前端的""
        cinema_name=cinema_name.replace("\"","");
        try {
            int cinema_id=cinemaAccService.findCinemaId(cinema_name);
            List<String> idByMovie = cinemaAccService.findMovieById(cinema_id);
            return idByMovie;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //-----------------------------修改、删除、增加-----------------------------------
    /**
     * 删除某个影院中的某个影片
     * @param cinema_id 影片id
     * @param movie_id 影院id
     */
    @PostMapping("/delCinemaAndMovie")
    public void delCinemaAndMovie(int cinema_id, int movie_id) {
        cinemaAndMovieService.delCinemaAndMovie(cinema_id,movie_id);
    }
    /**
     * 插入某个影院中没有的新电影
     * @param cinema_id 影院id
     * @param movie_id 电影id
     */
    @PostMapping("/addCinemaAndMovie")
    public void addCinemaAndMovie(int cinema_id, int movie_id) {
        cinemaAndMovieService.addCinemaAndMovie(cinema_id,movie_id);
    }
}
