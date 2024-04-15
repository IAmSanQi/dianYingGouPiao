package com.example.dianyinggoupiao_springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dianyinggoupiao_springboot.entity.Cinema;
import com.example.dianyinggoupiao_springboot.entity.CinemaAndSchedulingTable;
import com.example.dianyinggoupiao_springboot.entity.Scheduling;
import com.example.dianyinggoupiao_springboot.server.CAndSTableService;
import com.example.dianyinggoupiao_springboot.server.CinemaAccService;
import com.example.dianyinggoupiao_springboot.server.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/CinemaAndScheduling")
public class CAndSTableController {
    @Autowired
    private CAndSTableService cAndSTableService;

    @Autowired
    private CinemaAccService cinemaAccService;
    @Autowired
    private CinemaService cinemaService;

    /**
     * 格式化date
     * @param originalDateString 字符串日期
     * @return 字符串日期
     */
    public String initDate(String originalDateString){
        try {
            if (originalDateString.matches("\\d{4}-\\d{2}-\\d{2}")||originalDateString.matches("\\d{4}/\\d{2}/\\d{2}")) {
                // 如果日期字符串符合预期的格式，则直接返回
                return originalDateString;
            }else {
                Instant instant = Instant.parse(originalDateString);
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

                DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return localDateTime.format(targetFormatter);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return originalDateString;
        }


    }

    /**
     * 查找所有
     * @return List<CinemaAndSchedulingTable>
     */
    @PostMapping("/findAll")
    public List<CinemaAndSchedulingTable> findAll() {
        return cAndSTableService.findAll();
    }

    /**
     * 根据一个或多个id查找
     * @param id id
     * @return List<CinemaAndSchedulingTable>
     */
    @PostMapping("/findALlById")
    public List<CinemaAndSchedulingTable> findAllById(List<Integer> id) {
        return cAndSTableService.findAllById(id);
    }

    /**
     * 根据影院名称查询影院中所有排片信息
     * @param request 影院名称
     * @return 当前影院中所有排片信息
     */
    @PostMapping("/findSchedulingByCinemaName")
    public List<Scheduling>findSchedulingByName(@RequestBody Map<String,String> request){
        String cinema_name=request.get("data");
        //除去前端的""
        cinema_name=cinema_name.replace("\"","");
        try {
            int cinema_id=cinemaAccService.findCinemaId(cinema_name);
            List<Scheduling> schedulings = cAndSTableService.findSchedulingByCinema_id(cinema_id);
            return schedulings;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 根据 cinema_id 查询排片信息
     * @param cinema_id 影院id
     * @return List<Scheduling>
     */
    @PostMapping("/findSchedulingByCinemaId")
    public List<Scheduling>findSchedulingByCinemaId(@RequestParam("cinema_id") int cinema_id){
        try {
            List<Scheduling> schedulings = cAndSTableService.findSchedulingByCinema_id(cinema_id);
            return schedulings;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 根据影院id查询影院中影片名称等于movie_name排片信息
     * @param cinema_id 影院id
     * @param movie_name 影片名称
     * @return List<Scheduling>
     */
    @PostMapping("/findSchedulingByMovieName")
    public List<Scheduling>findSchedulingByMovieName(
            @RequestParam("cinema_id") int cinema_id,
            @RequestParam("movie_name") String movie_name
    ){
        try {
            List<Scheduling> schedulings = cAndSTableService.findSchedulingByCinema_id(cinema_id);
            List<Scheduling> schedulingList= new ArrayList<>();
            for (Scheduling scheduling : schedulings) {
                if (scheduling.getName().equals(movie_name)){
                    schedulingList.add(scheduling);
                }
            }
            return schedulingList;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 保存
     * @param request 保存的Scheduling数据和电影名称
     * @return 字符串
     */
    @PostMapping("/saveCinemaAndSchedulingTable")
    public boolean saveCinemaAndSchedulingTable(@RequestBody String request){
        JSONObject jsonObject = JSON.parseObject(request);
        //获取data
        String data = null;
        try {
            data = jsonObject.getString("data");
            JSONObject dataJson = JSON.parseObject(data);

            //获取data中的cinemaAccName
            String cinemaAccName = dataJson.getString("cinemaAccName");

            //获取data中的items
            JSONArray items = dataJson.getJSONArray("items");
            //获取items中的第一个元素
            JSONObject itemsJson = items.getJSONObject(0);

            //获取items中的属性
            String name = itemsJson.getString("name");

            //日期格式
            String date = itemsJson.getString("date");
            date=initDate(date);

            String startTime = itemsJson.getString("startTime");
            String endTime = itemsJson.getString("endTime");
            String movieTheater = itemsJson.getString("movieTheater");
            String priceStr = itemsJson.getString("price");
            String type = itemsJson.getString("type");

            //初始化数据
            CinemaAndSchedulingTable cinemaAndSchedulingTable = new CinemaAndSchedulingTable();
            Scheduling scheduling = new Scheduling();

            //获取Cinema
            int cinema_id = cinemaAccService.findCinemaId(cinemaAccName);
            Optional<Cinema> cinema1 = cinemaService.findById(cinema_id);

            //保存scheduling
            if (!date.equals("")&&!startTime.equals("")&&!endTime.equals("")&&!name.equals("")
                &&!type.equals("")&&!movieTheater.equals("")&&!priceStr.equals(""))
            {
                scheduling.setDate(date);
                scheduling.setStartTime(startTime);
                scheduling.setEndTime(endTime);
                scheduling.setName(name);
                scheduling.setType(type);
                scheduling.setMovieTheater(movieTheater);
                //获取精度更高的数据
                BigDecimal price = new BigDecimal(priceStr);
                scheduling.setPrice(price);
            }else return false;



            //保存 SchedulingData 和 CinemaData
            cinemaAndSchedulingTable.setSchedulingData(scheduling);
            cinemaAndSchedulingTable.setCinemaData(cinema1.orElse(null));
            //保存到数据库
            cAndSTableService.save(cinemaAndSchedulingTable);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * 删除某一条
     * @param request id数据
     * @return boolean
     */
    @PostMapping("/deleteCinemaAndSchedulingTable")
    public boolean deleteCinemaAndSchedulingTable(@RequestBody String request){
        JSONObject jsonObject = JSON.parseObject(request);
        String id = "";
        try {
            id = jsonObject.getString("data");
            if (!id.equals("")){
                cAndSTableService.deleteBySchedulingData_Id(Integer.parseInt(id));
            }else return false;
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * 删除某多条
     * @param request 多条数据id
     * @return boolean
     */
    @PostMapping("/delManyCinemaAndSchedulingTableData")
    public boolean delManyCinemaAndSchedulingTableData(@RequestBody String request){
        if (request.isEmpty()) return false;
        JSONObject jsonObject = JSON.parseObject(request);
        JSONArray data = jsonObject.getJSONArray("data");
        System.out.println(data);
        Integer[] dataArr = data.toArray(new Integer[0]);

        return cAndSTableService.delManyScheduling(Arrays.asList(dataArr));
    }


}
