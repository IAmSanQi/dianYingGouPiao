package com.example.dianyinggoupiao_springboot.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
@Entity
public class Movie_info {
    @Id
    private String name;
    private String type;
    private Timestamp time;
    private String play_time;
    private String location;
    private String introduce;
    private String timeString;
    private String Starring;

    public Movie_info() {
    }

    public Movie_info(
            String name,
            String type,
            Timestamp time,
            String play_time,
            String location,
            String introduce,
            String starring
    ) {
        this.name = name;
        this.type = type;
        this.time = time;
        this.play_time = play_time;
        this.location = location;
        this.introduce = introduce;
        this.Starring = starring;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getPlay_time() {
        return play_time;
    }

    public void setPlay_time(String play_time) {
        this.play_time = play_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }


    public String getStarring() {
        return Starring;
    }

    public void setStarring(String starring) {
        this.Starring = starring;
    }

    @Override
    public String toString() {
        return "Movie_info{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", play_time='" + play_time + '\'' +
                ", location='" + location + '\'' +
                ", introduce='" + introduce + '\'' +
                ", timeString='" + timeString + '\'' +
                ", Starring='" + Starring + '\'' +
                '}';
    }

}
