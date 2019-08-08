package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.repository.BonusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@Slf4j
@Service
public class BonusService {

    private BonusRepository bonusRepository;

    @Autowired
    public BonusService(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    @PostConstruct
    private void connectToDB() {

        if(bonusRepository.count()==0) {
            ArrayList<BonusModel> bonusModels = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                bonusModels.add(new BonusModel(i, i));
            }
            bonusRepository.saveAll(bonusModels);
        }
    }

    public Collection<BonusModel> getAllBonuses() {
        return bonusRepository.findAll();
    }

    public BonusModel chooseBonuse() {
        Collection<BonusModel> bonusModels = bonusRepository.findAll();
        int size = bonusModels.size();
        if (size > 0) {
            int id = (new Random()).nextInt(size);
            BonusModel[] objectBonusModel = bonusModels.toArray(new BonusModel[0]);
            if (objectBonusModel[id] != null) return objectBonusModel[id];
        }
        throw new NullPointerException();
    }

}
