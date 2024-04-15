package com.example.dianyinggoupiao_springboot.entity;

public class MovieAllInfo {
    private Integer id;
    private String name;
    private String ticketNumber;
    private String score;
    private String see;
    private String isBlockbuster;
    private String isRelease;
    private Movie_info movie_info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSee() {
        return see;
    }

    public void setSee(String see) {
        this.see = see;
    }

    public String getIsBlockbuster() {
        return isBlockbuster;
    }

    public void setIsBlockbuster(String isBlockbuster) {
        this.isBlockbuster = isBlockbuster;
    }

    public String getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(String isRelease) {
        this.isRelease = isRelease;
    }

    public Movie_info getMovie_info() {
        return movie_info;
    }

    public void setMovie_info(Movie_info movie_info) {
        this.movie_info = movie_info;
    }

    @Override
    public String toString() {
        return "MovieAllInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ticketNumber='" + ticketNumber + '\'' +
                ", score='" + score + '\'' +
                ", see='" + see + '\'' +
                ", isBlockbuster='" + isBlockbuster + '\'' +
                ", isRelease='" + isRelease + '\'' +
                ", movie_info=" + movie_info +
                '}';
    }
}
