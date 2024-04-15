package com.example.dianyinggoupiao_springboot.server;

import com.example.dianyinggoupiao_springboot.entity.Cinema;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CinemaService {
    Optional<Cinema> findById(Integer id);

    public int findCinemaId(String cinemaName);
    public List<Cinema> findCinemaAll();
    public Set<String> brandFilter();
    public Set<String> administrativeDistrictFilter();
    public Set<String> typeFilter();
    public List<Cinema> selectCinema(String brand,String administrativeDistrict,String type,String service);
    public List<Cinema> selectCinemaByScheduling(
            List<Cinema> list,
            String brand,
            String administrativeDistrict,
            String type,
            String service
    );

    public Cinema findCinemaByName(String name);

    public boolean save(Cinema cinema);
}
