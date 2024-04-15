package com.example.dianyinggoupiao_springboot.entity;

public class CinemaAndMovieTable {
    private int cinema_id;
    private int movie_id;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
    }

    @Override
    public String toString() {
        return "CinemaAndMovieTable{" +
                "cinema_id=" + cinema_id +
                ", movie_id=" + movie_id +
                '}';
    }
}
