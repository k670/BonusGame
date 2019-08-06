package com.example.demo.service;

import com.example.demo.repository.BonusRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class BonusService {

    @Autowired
    private BonusRepository bonusRepository;

    public BonusService(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    public ArrayList<Integer> getAllBonuses(){
        return bonusRepository.getBonuses();
    }

    public Map<String, Integer> chooseBonuse(){
        Map<String, Integer> bonusObj = new HashMap<>();
        int index = (new Random()).nextInt(bonusRepository.getBonuses().size());
        bonusObj.put("index",index);
        bonusObj.put("value",bonusRepository.getOneBonuse(index));
        return bonusObj;
    }

}
