package com.example.dianyinggoupiao_springboot.server;

import com.example.dianyinggoupiao_springboot.entity.CinemaAcc;

import java.util.List;

public interface CinemaAccService {
    public List<CinemaAcc> findCinemaAcc();
    public int findCinemaId(String cinemaName);
    public List<String> findMovieById(int cinema_id);
    public boolean isUsername(String accName);
}
