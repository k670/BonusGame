package com.example.demo.service;

import com.example.demo.model.BonusModel;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;

@Slf4j
class CounterTask extends RecursiveTask<HashMap<Double, Long>> {
    private static final long THRESHOLD = 100;
    private long counter;
    private Collection<BonusModel> bonusModelCollection;

    private BonusService bonusService;

    public CounterTask(Collection<BonusModel> bonusModelCollection, long countOfRep, BonusService bonusService) {
        this.bonusModelCollection = bonusModelCollection;
        this.bonusService = bonusService;
        counter = countOfRep / THRESHOLD;
    }

    @Override
    public HashMap<Double, Long> compute() {

        if (counter == 0) {

            return null;
        }
        if (counter < 2) {
            HashMap<Double, Long> bonuseMap = new HashMap<>();
            bonusModelCollection.forEach(bonusModel -> {
                bonuseMap.put(bonusModel.getValue(), 0L);
            });

            for (long i = 0; i < THRESHOLD; i++) {
                double c = bonusService.chooseBonuse().getValue();
                bonuseMap.put(c, (1 + bonuseMap.get(c)));

            }
            return bonuseMap;

        } else {
            Optional<HashMap<Double, Long>> newBonusMap1 = Optional.ofNullable(new CounterTask(bonusModelCollection, (counter >>> 1) * THRESHOLD, bonusService).invoke());
            Optional<HashMap<Double, Long>> newBonusMap2 = Optional.ofNullable(new CounterTask(bonusModelCollection, (counter - (counter >>> 1)) * THRESHOLD, bonusService).invoke());
            if (newBonusMap1.isPresent() && newBonusMap2.isPresent()) {
                newBonusMap1.get().keySet().forEach(k -> {
                    newBonusMap1.get().put(k, newBonusMap1.get().get(k) + newBonusMap2.get().get(k));
                });
                return newBonusMap1.get();
            }
            return null;
        }
    }


}
