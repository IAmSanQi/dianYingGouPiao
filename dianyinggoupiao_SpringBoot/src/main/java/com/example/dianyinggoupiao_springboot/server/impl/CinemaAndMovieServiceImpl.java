package com.example.dianyinggoupiao_springboot.server.impl;

import com.example.dianyinggoupiao_springboot.entity.MovieAllInfo;
import com.example.dianyinggoupiao_springboot.mapper.CinemaAndMovieMapper;
import com.example.dianyinggoupiao_springboot.server.CinemaAndMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaAndMovieServiceImpl implements CinemaAndMovieService {
    @Autowired
    private CinemaAndMovieMapper cinemaAndMovieMapper;

    @Override
    public List<MovieAllInfo> findCinemaAllMovieInfo(int cinema_id) {
        return cinemaAndMovieMapper.findCinemaAllMovieInfo(cinema_id);
    }

    @Override
    public void delCinemaAndMovie(int cinema_id, int movie_id) {
        cinemaAndMovieMapper.delCinemaAndMovie(cinema_id,movie_id);
    }

    @Override
    public void addCinemaAndMovie(int cinema_id, int movie_id) {
        cinemaAndMovieMapper.addCinemaAndMovie(cinema_id,movie_id);
    }
}
