package com.example.dianyinggoupiao_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DianyinggoupiaoSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DianyinggoupiaoSpringBootApplication.class, args);
    }

}
