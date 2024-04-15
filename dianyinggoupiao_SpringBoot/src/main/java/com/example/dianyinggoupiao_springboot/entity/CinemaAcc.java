package com.example.dianyinggoupiao_springboot.entity;

public class CinemaAcc {
    private int cinema_id;
    private String name;
    private String password;

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CinemaAcc{" +
                "cinema_id=" + cinema_id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
