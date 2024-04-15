package com.example.dianyinggoupiao_springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dianyinggoupiao_springboot.entity.CinemaAndSchedulingTable;
import com.example.dianyinggoupiao_springboot.entity.Scheduling;
import com.example.dianyinggoupiao_springboot.repositroy.CAndSTableRepository;
import com.example.dianyinggoupiao_springboot.server.CAndSTableService;
import com.example.dianyinggoupiao_springboot.server.CinemaAccService;
import com.example.dianyinggoupiao_springboot.server.impl.SchedulingServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {
    @Autowired
    private SchedulingServiceImpl schedulingService;

    @Autowired
    private CinemaAccService cinemaAccService;

    @Autowired
    private CAndSTableRepository cAndSTableRepository;

    @Autowired
    private CAndSTableController controller;


    public List<Scheduling> getAll(){
        return schedulingService.findSchedulingAll();
    }

    /**
     * 查询分页后当前页的排片数据
     * @param request 前端数据
     * @return 分页后当前页的排片数据
     */
    @PostMapping("/getSchedulingPage")
    public List<Scheduling> getSchedulingPage(@RequestBody String request){
        //数据json化
        JSONObject jsonObject = JSON.parseObject(request);

        // 获取 "data" 字段的值
        String dataString = jsonObject.getString("data");

        // 解析 "data" 字段中的 JSON 数据
        JSONObject dataObject = JSON.parseObject(dataString);

        //获取jsonObject中的属性
        JSONObject pageData=dataObject.getJSONObject("pageable");
        String cinemaACCName = dataObject.getString("cinemaAccName");

        int pageSize= 5 ;
        try {
            pageSize = Integer.parseInt(pageData.getString("pageSize"));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        int currentPage= 1;
        try {
            currentPage = Integer.parseInt(pageData.getString("currentPage"));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        int cinema_id=cinemaAccService.findCinemaId(cinemaACCName);
        Pageable pageable= PageRequest.of(currentPage-1,pageSize);
        Page<CinemaAndSchedulingTable> byCinemaDataContaining = cAndSTableRepository.findByCinemaData_CinemaId(cinema_id, pageable);
        List<Scheduling> schedulingList= new ArrayList<>();
        for (CinemaAndSchedulingTable cinemaAndSchedulingTable : byCinemaDataContaining) {
            schedulingList.add(cinemaAndSchedulingTable.getSchedulingData());
        }
        return schedulingList;
    }

    /**
     * 修改排片数据
     * @param request 排片数据
     * @return 放回 boolean数据
     */
    @PostMapping("/saveScheduling")
    public boolean modifyScheduling(@RequestBody String request){

        try {
            JSONObject jsonObject = JSON.parseObject(request);
            String data = jsonObject.getString("data");
            JSONObject jsonData = JSON.parseObject(data);
            String id = jsonData.getString("id");
            String name = jsonData.getString("name");
            String date = jsonData.getString("date");
            String movieTheater = jsonData.getString("movieTheater");
            String startTime = jsonData.getString("startTime");
            String endTime = jsonData.getString("endTime");
            String priceStr = jsonData.getString("price");
            String type = jsonData.getString("type");

            Scheduling scheduling = new Scheduling();
            if (!id.equals("")){
                scheduling.setId(Integer.parseInt(id));
                scheduling.setName(name);

                date=controller.initDate(date);
                scheduling.setDate(date);
                scheduling.setMovieTheater(movieTheater);
                scheduling.setStartTime(startTime);
                scheduling.setEndTime(endTime);

                BigDecimal price = new BigDecimal(priceStr);
                scheduling.setPrice(price);
                scheduling.setType(type);
            }else return false;

            return schedulingService.saveScheduling(scheduling);
        } catch (Exception e) {
            System.out.println("8888888888888888888888888");
            System.out.println(e.getMessage());
        }

       return false;
    }
}
