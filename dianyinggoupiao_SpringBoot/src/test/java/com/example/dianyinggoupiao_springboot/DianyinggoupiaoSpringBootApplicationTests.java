package com.example.dianyinggoupiao_springboot;


import com.example.dianyinggoupiao_springboot.controller.CinemaAndMovieController;
import com.example.dianyinggoupiao_springboot.controller.ImageUploadController;
import com.example.dianyinggoupiao_springboot.controller.OrderController;
import com.example.dianyinggoupiao_springboot.entity.*;
import com.example.dianyinggoupiao_springboot.mapper.*;
import com.example.dianyinggoupiao_springboot.server.*;
import com.example.dianyinggoupiao_springboot.util.ALiPayUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
class DianyinggoupiaoSpringBootApplicationTests {

    @Autowired
    ALiPayUtil aLiPayUtil;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderController orderController;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    MovieMapper movieMapper;
    @Autowired
    CinemaAccMapper cinemaAccMapper;
    @Autowired
    MovieService movieService;
    @Autowired
    UserAccMapper userAccMapper;
    @Autowired
    CAndSTableService cAndSTableService;
    @Autowired
    CinemaService cinemaService;
    @Autowired
    CinemaAccService cinemaAccService;

    @Autowired
    ImageUploadService imageUploadService;
    @Autowired
    ManageAccService manageAccService;

    @Autowired
    ManageAccMapper manageAccMapper;

    @Autowired
    CinemaAndMovieMapper cinemaAndMovieMapper;
    @Test
    void test() {
//        RandomStringGenerator rm = new RandomStringGenerator();
//        String randomString="";
////        rm.generateUniqueRandomString(movieService);
//        for (int i = 0; i < 40; i++) {
//            randomString =rm.RandomStringGenerator();
//            System.out.println(randomString);
//        }
        List<Movie> movie=movieService.findMovieAll();
        String[] mySqlImageId = new String[movie.size()];
        for (int i = 0; i < mySqlImageId.length-1; i++) {
            if (movie.get(i).getImageId()!=null){
                mySqlImageId[i]=movie.get(i).getImageId();
            }else {
                mySqlImageId[i]="";
            }
        }
        System.out.println(movieMapper.findImagesById(mySqlImageId[0]));

    }
    @Test
    void test001(){
        int cinema_id= cinemaService.findCinemaByName("SFC上影国际影城（吴江汾湖店）").getCinemaId();
        System.out.println(cinema_id);
        List<Scheduling> schedulings = cAndSTableService.findSchedulingByCinema_id(cinema_id);
        for (Scheduling S : schedulings) {
            System.out.println("scheduling: "+ S);
        }
    }

    @Test
    void test002(){
        System.out.println("==============================================================");

        System.out.println("===============================================================");
    }

}
