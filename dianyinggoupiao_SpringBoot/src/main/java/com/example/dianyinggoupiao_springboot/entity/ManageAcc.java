package com.example.dianyinggoupiao_springboot.entity;

public class ManageAcc {
    private int manage_id;
    private String name;
    private String password;
    private String avatar_id;

    public int getManage_id() {
        return manage_id;
    }

    public void setManage_id(int manage_id) {
        this.manage_id = manage_id;
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
        return "ManageAcc{" +
                "manage_id=" + manage_id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getAvatar_id() {
        return avatar_id;
    }

    public void setAvatar_id(String avatar_id) {
        this.avatar_id = avatar_id;
    }
}
