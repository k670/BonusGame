package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;


@SpringBootApplication
@EnableMapRepositories
public class BonusServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BonusServiceApplication.class, args);
    }

}
