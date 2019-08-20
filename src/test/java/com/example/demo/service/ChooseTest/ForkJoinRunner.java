package com.example.demo.service.ChooseTest;

import com.example.demo.model.BonusModel;
import com.example.demo.service.BonusService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ForkJoinTask;

public @Slf4j
class ForkJoinRunner {
    public HashMap<Double, Long> run(Collection<BonusModel> bonusModelCollection, long countOfRep, BonusService bonusService) {
        ForkJoinTask<HashMap<Double, Long>> mapForkJoinTask = new CounterTask(bonusModelCollection, countOfRep, bonusService);
        mapForkJoinTask.fork();
        HashMap<Double, Long> countMap = mapForkJoinTask.join();
        bonusModelCollection.forEach(bm -> {
            log.error("{} - {}", bm.getValue(), ((100.0 * countMap.get(bm.getValue())) / countOfRep));
        });
        return countMap;
    }
}