package com.example.dianyinggoupiao_springboot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;

@Entity
@Table(name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinema_id")
    private int cinemaId;
    private String name;
    private String location;
    private String brand;
    @Column(name = "administrative_district")
    private String administrativeDistrict;
    private String type;
    private String rebook;
    @Column(name = "return_ticket")
    private String returnTicket;
    private String price;
    private String phone;
    @Column(name = "avatar_id")
    private String avatarId;

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAdministrativeDistrict() {
        return administrativeDistrict;
    }

    public void setAdministrativeDistrict(String administrativeDistrict) {
        this.administrativeDistrict = administrativeDistrict;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRebook() {
        return rebook;
    }

    public void setRebook(String rebook) {
        this.rebook = rebook;
    }

    public String getReturnTicket() {
        return returnTicket;
    }

    public void setReturnTicket(String returnTicket) {
        this.returnTicket = returnTicket;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString() {
        return "Cinema{" +
                "cinema_id=" + cinemaId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", brand='" + brand + '\'' +
                ", administrativeDistrict='" + administrativeDistrict + '\'' +
                ", type='" + type + '\'' +
                ", rebook='" + rebook + '\'' +
                ", returnTicket='" + returnTicket + '\'' +
                ", price='" + price + '\'' +
                ", phone='" + phone + '\'' +
                ", avatarId='" + avatarId + '\'' +
                '}';
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }
}
