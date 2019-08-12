package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.repository.BonusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

@Slf4j
@Service
public class BonusService {

    private static final Random RANDOM = new Random();
    private BonusRepository bonusRepository;

    private NavigableMap<Integer, Integer> navigableMap;

    @Autowired
    public BonusService(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    @PostConstruct
    protected void createPercentMap() {
        BonusModel[] objectBonusModel = bonusRepository.findAll().toArray(new BonusModel[0]);
        navigableMap = new TreeMap<>();
        if (objectBonusModel.length == 0) {
            return;
        }
        navigableMap.put(objectBonusModel[0].getChance(), objectBonusModel[0].getId());
        for (int i = 1; i < objectBonusModel.length; i++) {
            navigableMap.put(objectBonusModel[i].getChance() + navigableMap.lastEntry().getKey(), objectBonusModel[i].getValue());
        }
    }

/*
    @PostConstruct
    private void connectToDB() {

        if (bonusRepository.count() == 0) {
            ArrayList<BonusModel> bonusModels = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                bonusModels.add(new BonusModel(i, i, (5 - i) * 10));
            }
            bonusRepository.saveAll(bonusModels);
        }
    }
*/

    public Collection<BonusModel> getAllBonuses() {
        return bonusRepository.findAll();
    }

    public int chooseBonuse() {
        if (navigableMap.isEmpty()) {
            throw new NullPointerException();
        }
        return generateId(navigableMap.lastKey());

    }

    private int generateId(int chanceSum) {
        int chance = RANDOM.nextInt(chanceSum);
        return navigableMap.higherEntry(chance).getValue();
    }

}
