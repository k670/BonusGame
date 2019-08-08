package com.example.demo;


import com.example.demo.context.BonusServiceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
public class BonusServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BonusServiceApplication.class, args);
    }

}
