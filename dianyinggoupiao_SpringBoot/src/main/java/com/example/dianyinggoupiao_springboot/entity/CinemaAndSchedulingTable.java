package com.example.dianyinggoupiao_springboot.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cinema_scheduling_table")
public class CinemaAndSchedulingTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cinema_id", referencedColumnName = "cinema_id")
    private Cinema cinemaData;

    @OneToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "scheduling_id")
    private Scheduling schedulingData;

}
