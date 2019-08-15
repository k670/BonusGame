package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.repository.BonusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;

@Slf4j
@Service("bonusService")
public class BonusService {

    private static final Random RANDOM = new Random();
    private BonusRepository bonusRepository;

    private Map<Double, BonusModel> bonusModelMap;
    private NavigableMap<Double, Double> navigableMap;


    @Autowired
    public BonusService(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    public void createPercentMap() {
        BonusModel[] objectBonusModel = bonusModelMap.values().toArray(new BonusModel[0]);
        navigableMap = new TreeMap<>();
        if (objectBonusModel.length == 0) {
            return;
        }
        navigableMap.put(objectBonusModel[0].getChance(), objectBonusModel[0].getValue());
        for (int i = 1; i < objectBonusModel.length; i++) {
            navigableMap.put(objectBonusModel[i].getChance() + navigableMap.lastEntry().getKey(), objectBonusModel[i].getValue());
        }
    }


    public Collection<BonusModel> getAllBonuses() {
        return bonusModelMap.values();
    }

    public BonusModel chooseBonuse() {
        return chooseBonuse(1.0,1);
    }

    public BonusModel chooseBonuse(double bet, int userId) {
        if (navigableMap.isEmpty()) {
            throw new RuntimeException();
        }
        Optional<BonusModel> bonusModel = Optional.ofNullable(bonusModelMap.get(generateMultipl(navigableMap.lastKey())));
        if (bonusModel.isEmpty()) return null;
        double idEffect = 0.000_000_01;
        bonusModel.get().setChance(bet * (bonusModel.get().getValue()+(userId*idEffect)));
        return bonusModel.get();

    }



    private double generateMultipl(double chanceSum) {
        double chance = chanceSum * RANDOM.nextDouble();
        return navigableMap.higherEntry(chance).getValue();
    }

    @PostConstruct
    @Scheduled(initialDelay = 1000)
    public void readDB() {
        bonusModelMap = new HashMap<>();
        Collection<BonusModel> collection = bonusRepository.findAll();
        collection.forEach(bm -> {
            bonusModelMap.put(bm.getValue(), bm);
        });
        createPercentMap();
    }

}
