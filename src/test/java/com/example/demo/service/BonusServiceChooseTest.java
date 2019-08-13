package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.repository.BonusRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BonusServiceChooseTest {
    @Autowired
    private BonusService bonusService;

    @Autowired
    private BonusRepository bonusRepository;

    private HashMap<Integer, Long> countMap;
    private Collection<BonusModel> bonusModelCollection;

    private long rep = 10_000_000_000L;

    @Before
    public void before() {
        bonusModelCollection = bonusRepository.findAll();
        countMap = new HashMap<>();
        bonusModelCollection.forEach(bonusModel -> {
            countMap.put(bonusModel.getValue(), 0L);
        });
    }

    @Test
    public void doIt() throws ExecutionException, InterruptedException {
        int countThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(countThreads);
        Callable<HashMap<Integer, Long>> mapCallable = new Callable<HashMap<Integer, Long>>() {
            @Override
            public HashMap<Integer, Long> call() throws Exception {
                log.info(Thread.currentThread().getName());
                HashMap<Integer, Long> bonuseMap = new HashMap<>();
                bonusModelCollection.forEach(bonusModel -> {
                    bonuseMap.put(bonusModel.getValue(), 0L);
                });
                for (long i = 0; i < rep / countThreads; i++) {
                    int c = bonusService.chooseBonuse();
                    bonuseMap.put(c, (1 + bonuseMap.get(c)));

                }
                return bonuseMap;
            }
        };
        Future<HashMap<Integer, Long>>[] futures = new Future[countThreads];
        for (int i = 0; i < countThreads; i++) {

            futures[i] = executorService.submit(mapCallable);

        }

        for (Future<HashMap<Integer, Long>> future : futures) {

            while (!future.isDone()){}
            if (future.isDone()) {
                HashMap<Integer, Long> hashMap = future.get();
                bonusModelCollection.forEach(bonusModel -> {
                    int c = bonusModel.getValue();
                    countMap.put(c, (countMap.get(c)) + hashMap.get(c));
                });
            }
        }
   /*     for (long i = 0; i < rep; i++) {
            int c = bonusService.chooseBonuse();
            countMap.put(c, (1 + countMap.get(c)));
           *//* if (i % 100_000_000 == 0) {
                log.info("{}", c);
            }*//*
        }*/

        sleep(1000);


        bonusModelCollection.forEach(bm -> {
            log.info(bm.getValue() + " " + 100.0*countMap.getOrDefault(bm.getValue(), 0L)/rep);
        });

    }

    @After
    public void after() throws InterruptedException {

        /*bonusModelCollection.forEach(bm -> {
            log.error("{} - {}", bm.getValue(), ((100.0 * countMap.get(bm.getValue())) / rep));
        });*/
    }

}
