package com.example.dianyinggoupiao_springboot.server.impl;

import com.example.dianyinggoupiao_springboot.entity.CinemaAcc;
import com.example.dianyinggoupiao_springboot.mapper.CinemaAccMapper;
import com.example.dianyinggoupiao_springboot.server.CinemaAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaAccServiceImpl implements CinemaAccService {
    @Autowired
    private CinemaAccMapper cinemaAccMapper;

    @Override
    public List<CinemaAcc> findCinemaAcc() {
        return cinemaAccMapper.findCinemaAcc();
    }

    @Override
    public int findCinemaId(String cinemaName) {
        return cinemaAccMapper.findCinemaId(cinemaName);
    }

    @Override
    public List<String> findMovieById(int cinema_id) {
        return cinemaAccMapper.findMovieById(cinema_id);
    }

    @Override
    public boolean isUsername(String accName) {
        List<CinemaAcc> cinemaAcc = cinemaAccMapper.findCinemaAcc();
        for (CinemaAcc acc : cinemaAcc) {
            if (acc.getName().equals(accName)){
                return true;
            }
        }
        return false;
    }
}
