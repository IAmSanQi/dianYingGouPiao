package com.example.dianyinggoupiao_springboot.server.impl;

import com.example.dianyinggoupiao_springboot.entity.Cinema;
import com.example.dianyinggoupiao_springboot.mapper.CinemaAccMapper;
import com.example.dianyinggoupiao_springboot.repositroy.CinemaRepository;
import com.example.dianyinggoupiao_springboot.server.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CinemaAccMapper cinemaAccMapper;

    /**
     * 字符串筛选器
     * @param array String[]
     * @return Set<String>
     */
    public Set<String> StringFilter(String[] array){
        Set<String> uniqueStrings = new HashSet<>(Arrays.asList(array));
        return uniqueStrings;
    }

    /**
     * 根据id查找 Cinema
     * @param id  CinemaId
     * @return Optional<Cinema>
     */
    @Override
    public Optional<Cinema> findById(Integer id) {
        return cinemaRepository.findById(id);
    }

    @Override
    public int findCinemaId(String cinemaName) {
        return cinemaAccMapper.findCinemaId(cinemaName);
    }

    /**
     * 返回影院的所有信息
     * @return List<Cinema>
     */
    @Override
    public List<Cinema> findCinemaAll() {
        return cinemaRepository.findAll();
    }
    /**
     * 返回影院的brand不重复信息
     * @return Set<String>
     */
    @Override
    public Set<String> brandFilter() {
        List<Cinema> list=cinemaRepository.findAll();
        String[] brand=new String[list.size()];
        int index=0;
        for (Cinema c:list) {
            brand[index]=c.getBrand();
            index++;
        }
        return StringFilter(brand);
    }
    /**
     * 返回影院的administrativeDistrict不重复信息
     * @return Set<String>
     */
    @Override
    public Set<String> administrativeDistrictFilter() {
        List<Cinema> list=cinemaRepository.findAll();
        String[] administrativeDistrict=new String[list.size()];
        int index=0;
        for (Cinema c:list) {
            administrativeDistrict[index]=c.getAdministrativeDistrict();
            index++;
        }
        return StringFilter(administrativeDistrict);
    }
    /**
     * 返回影院的type不重复信息
     * @return Set<String>
     */
    @Override
    public Set<String> typeFilter() {
        List<Cinema> list=cinemaRepository.findAll();
        String[] type=new String[list.size()];
        int index=0;
        for (Cinema c:list) {
            if (c.getType()!=null){
                if (c.getType().contains(",")){
                    String[] str=c.getType().split(",");
                    for (String s : str) {
                        type[index] = s;
                        index++;
                    }
                }else {
                    type[index]=c.getType();
                    index++;
                }
            }
        }
        return StringFilter(type);
    }

    /**
     * 筛选
     * @param brand 品牌，
     * @param administrativeDistrict 行政区
     * @param type 类型
     * @param service 服务
     * @return 对应的影院信息
     */
    @Override
    public List<Cinema> selectCinema(String brand,String administrativeDistrict,String type,String service) {
        List<Cinema> list=cinemaRepository.findAll();
        return selectCinemaByScheduling(list,brand,administrativeDistrict,type,service);
    }

    /**
     * 筛选
     * @param brand 品牌，
     * @param administrativeDistrict 行政区
     * @param type 类型
     * @param service 服务
     * @return 对应的影院信息
     */
    @Override
    public List<Cinema> selectCinemaByScheduling(
            List<Cinema> list,
            String brand,
            String administrativeDistrict,
            String type,
            String service)
    {
        List<Cinema> serviceData=new ArrayList<>();
        List<Cinema> Data=new ArrayList<>();

        switch (service) {
            case "全部":
                serviceData.addAll(list);
                break;
            case "1":
                for (Cinema c : list) {
                    if (c.getRebook()!=null&&c.getRebook().equals("1")) {
                        serviceData.add(c);
                    }
                }
                break;
            case "2":
                for (Cinema c : list) {
                    if (c.getReturnTicket()!=null&&c.getReturnTicket().equals("1")) {
                        serviceData.add(c);
                    }
                }
                break;
        }
        if (!brand.equals("全部")){
            if (!administrativeDistrict.equals("全部")){
                if (!type.equals("全部")){
                    //根据品牌，行政区，类型来查询
                    for (Cinema c: serviceData) {
                        if (c.getBrand()!=null&&c.getAdministrativeDistrict()!=null&&c.getType()!=null){
                            if (c.getBrand().equals(brand)&&
                                    c.getAdministrativeDistrict().equals(administrativeDistrict)&&
                                    c.getType().contains(type))
                            {
                                Data.add(c);
                            }
                        }
                    }
                }else {
                    //根据品牌，行政区来查询
                    for (Cinema c: serviceData) {
                        if (c.getBrand()!=null&&c.getAdministrativeDistrict()!=null){
                            if (c.getBrand().equals(brand)&&
                                    c.getAdministrativeDistrict().equals(administrativeDistrict))
                            {
                                Data.add(c);
                            }
                        }
                    }
                }
            }else {
                if (!type.equals("全部")){
                    //根据品牌，类型来查询
                    for (Cinema c: serviceData) {
                        if (c.getBrand()!=null&&c.getType()!=null){
                            if (c.getBrand().equals(brand)&&
                                    c.getType().contains(type))
                            {
                                Data.add(c);
                            }
                        }
                    }
                }else {
                    //根据品牌来查询
                    for (Cinema c: serviceData) {
                        if (c.getBrand()!=null){
                            if (c.getBrand().equals(brand))
                            {
                                Data.add(c);
                            }
                        }
                    }
                }
            }
        }else if (!administrativeDistrict.equals("全部")){
            if (!type.equals("全部")){
                //根据行政区，类型来查询
                for (Cinema c: serviceData) {
                    if (c.getAdministrativeDistrict()!=null&&c.getType()!=null){
                        if (c.getAdministrativeDistrict().equals(administrativeDistrict)&& c.getType().contains(type)) {
                            Data.add(c);
                        }
                    }
                }
            }else {
                //根据行政区来查询
                for (Cinema c: serviceData) {
                    if (c.getAdministrativeDistrict()!=null){
                        if (c.getAdministrativeDistrict().equals(administrativeDistrict)){
                            Data.add(c);
                        }
                    }
                }
            }
        }else {
            //根据类型来查询
            if (!type.equals("全部")){
                for (Cinema c: serviceData) {
                    if (c.getType()!=null){
                        if (c.getType().contains(type)) {
                            Data.add(c);
                        }
                    }
                }
            }else {
                Data.addAll(serviceData);
            }
        }
        return Data;
    }

    /**
     * 根据影院名称查询
     * @param name 账号
     * @return Cinema
     */
    @Override
    public Cinema findCinemaByName(String name) {

        Optional<Cinema> cinema = cinemaRepository.findByName(name);
        return cinema.orElse(null);
    }



    /**
     * 保存一条 cinema 数据
     * @param cinema cinema数据
     * @return boolean
     */
    @Override
    public boolean save(Cinema cinema) {
        try {
            cinemaRepository.save(cinema);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
