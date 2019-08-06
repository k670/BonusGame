package com.example.demo.controller;

import com.example.demo.repository.BonusRepository;
import com.example.demo.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class BonusGameController {
    @Autowired
    private BonusService bonusService;

    public BonusGameController(BonusService bonusService){
        this.bonusService = bonusService;
    }

    @GetMapping(path = "/bonus")
    public ArrayList<Integer> getAllBonuses() {

        return bonusService.getAllBonuses();
    }

    @PostMapping(path = "/bonus")
    public Map<String, Integer> chooseBonuse() {
        return bonusService.chooseBonuse();
    }
}
