/*
package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.repository.BonusRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

@Component("simpleBonusChooseAlg")
public class SimpleBonusChooseAlgorithm implements BonusChoosable {

    private static final Random RANDOM = new Random();
    private BonusRepository bonusRepository;

    private NavigableMap<Double, Double> navigableMap;

    public SimpleBonusChooseAlgorithm(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    @PostConstruct
    public void createPercentMap() {
        BonusModel[] objectBonusModel = bonusRepository.findAll().toArray(new BonusModel[0]);
        navigableMap = new TreeMap<>();
        if (objectBonusModel.length == 0) {
            return;
        }
        navigableMap.put(objectBonusModel[0].getChance(), objectBonusModel[0].getValue());
        for (int i = 1; i < objectBonusModel.length; i++) {
            navigableMap.put(objectBonusModel[i].getChance() + navigableMap.lastEntry().getKey(), objectBonusModel[i].getValue());
        }
    }


    public BonusModel chooseBonuse() {
        if (navigableMap.isEmpty()) {
            throw new NullPointerException();
        }
        BonusModel bonusModel = bonusRepository.findBonusModelByValue(generateMultipl(navigableMap.lastKey())).get();
        if (bonusModel.getName() == null) {
            bonusModel.setName("mx" + Math.round(bonusModel.getValue()));
        }
        return bonusModel;

    }

    private double generateMultipl(double chanceSum) {
        double chance = chanceSum * RANDOM.nextDouble();
        return navigableMap.higherEntry(chance).getValue();
    }
}
*/
