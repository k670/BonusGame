package com.example.demo.service;

import com.example.demo.model.BonusModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BonusServiceChooseTest {
/*
    @Autowired
    @Qualifier("bonusService")
    private BonusChoosable bonusChoosable;*/

    @Autowired
    private BonusService bonusService;

    private HashMap<Double, Long> countMap;

    private long rep = 100_000_000L;

    @Before
    public void before() {
        countMap = new HashMap<>();
    }


    @Test
    public void doIt() throws ExecutionException, InterruptedException {

        int countThreads = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(countThreads);
        CompletionService<HashMap<Double, Long>> executorService = new ExecutorCompletionService<>(executor);

        runTasks(countThreads, executorService, createCallable(rep / countThreads, () -> bonusService.chooseBonuse().getValue()));

        addAllResults(countThreads, executorService);
        executor.shutdown();
        checkResult();
        ;

    }

    @After
    public void after() {

        showResult();
    }

    private <T> Callable createCallable(long count, Callable<T> func) {
        return () -> {
            HashMap<T, Long> bonuseMap = new HashMap<>();
            for (long i = 0; i < count; i++) {
                T chooseBonuse = func.call();
                bonuseMap.merge(chooseBonuse, 1L, Long::sum);
            }
            return bonuseMap;
        };
    }

    private void runTasks(int countThreads, CompletionService<HashMap<Double, Long>> executorService, Callable task) {
        for (int i = 0; i < countThreads; i++) {
            executorService.submit(task);
        }
    }

    private void addAllResults(int countThreads, CompletionService<HashMap<Double, Long>> executorService) throws InterruptedException, ExecutionException {
        for (int i = 0; i < countThreads; i++) {
            Future<HashMap<Double, Long>> future = executorService.take();
            future.get().forEach((key, value) -> {
                countMap.merge(key, value, Long::sum);
            });
        }
    }

    private void checkResult() {
        double sumChance = bonusService.getAllBonuses().stream().mapToDouble(BonusModel::getChance).sum();
        double delta = 0.0331 * rep;
        NavigableSet<Double> realDelta = new TreeSet<>();
        bonusService.getAllBonuses().forEach(bm -> {
            double expected = rep * bm.getChance() / sumChance;
            double actual = countMap.get(bm.getValue());
            Assert.assertEquals(expected, actual, delta);
            realDelta.add(Math.abs(actual-expected));
        });
        log.info("Delta: "+realDelta.first()/rep+" - "+realDelta.last()/rep);
    }

    private void showResult() {
        countMap.forEach((key, value) -> {
            log.info(key + " " + 100.0 * value / rep);
        });
    }

}

;