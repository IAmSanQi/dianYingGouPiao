package com.example.dianyinggoupiao_springboot.entity;

public class Movie {
    private Integer id;
    private String name;
    private String ticketNumber;
    private String score;
    private String see;
    private String isBlockbuster;
    private String isRelease;
    private String imageId;

    public Movie() {

    }

    public Movie(String name, String ticketNumber, String score, String see, String isBlockbuster, String isRelease) {
        this.name = name;
        this.ticketNumber = ticketNumber;
        this.score = score;
        this.see = see;
        this.isBlockbuster = isBlockbuster;
        this.isRelease = isRelease;
    }

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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ticketNumber='" + ticketNumber + '\'' +
                ", score='" + score + '\'' +
                ", see='" + see + '\'' +
                ", isBlockbuster='" + isBlockbuster + '\'' +
                ", isRelease='" + isRelease + '\'' +
                ", isRelease='" + imageId + '\'' +
                '}';
    }

}
