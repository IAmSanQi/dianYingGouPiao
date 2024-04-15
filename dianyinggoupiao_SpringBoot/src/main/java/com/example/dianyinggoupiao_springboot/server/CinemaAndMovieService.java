package com.example.dianyinggoupiao_springboot.server;

import com.example.dianyinggoupiao_springboot.entity.MovieAllInfo;

import java.util.List;

public interface CinemaAndMovieService {
    public List<MovieAllInfo> findCinemaAllMovieInfo(int cinema_id);
    public void delCinemaAndMovie(int cinema_id,int movie_id);
    public void addCinemaAndMovie(int cinema_id,int movie_id);
}
