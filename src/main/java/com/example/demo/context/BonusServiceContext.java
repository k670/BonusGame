package com.example.demo.context;

import com.example.demo.controller.BonusGameController;
import com.example.demo.repository.BonusRepository;
import com.example.demo.service.BonusService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusServiceContext {

    @Bean
    public BonusRepository bonusRepository(){
        return new BonusRepository();
    }

    @Bean
    public BonusService bonusService(BonusRepository bonusRepository){
        return new BonusService(bonusRepository);
    }

}
