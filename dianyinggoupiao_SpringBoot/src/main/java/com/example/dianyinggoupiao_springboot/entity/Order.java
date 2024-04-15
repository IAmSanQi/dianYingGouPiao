package com.example.dianyinggoupiao_springboot.entity;

public class Order {
    private int id;
    private String user_id;
    private String order_id;
    private String movieName;
    private String cinemaName;
    private String cinemaHall;
    private String seat;
    private String date;
    private String money;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(String cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", order_id='" + order_id + '\'' +
                ", movieName='" + movieName + '\'' +
                ", cinemaName='" + cinemaName + '\'' +
                ", cinemaHall='" + cinemaHall + '\'' +
                ", seat='" + seat + '\'' +
                ", date='" + date + '\'' +
                ", money='" + money + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
