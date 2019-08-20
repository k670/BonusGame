package com.example.demo.service.ChooseTest;

import com.example.demo.model.BonusModel;
import com.example.demo.service.BonusService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BonusServiceChooseTest {

    @Autowired
    private BonusService bonusService;

    private HashMap<Double, Long> countMap;

    private int countThreads = Runtime.getRuntime().availableProcessors();
    private long rep = 100_000_000L;
    private ExecutorService executor;
    private CompletionService<HashMap<Double, Long>> executorService;

    @Before
    public void beforeEach() {
        countMap = new HashMap<>();
        executor = Executors.newFixedThreadPool(countThreads);
        executorService = new ExecutorCompletionService<>(executor);
    }

    @After
    public void afterEach() {

        showResult();
    }


    @Test
    public void simpleAlgorithm() throws ExecutionException, InterruptedException {
        log.info("simple\n");
        doIt(1);
    }

    @Test
    public void doubleAlgorithm() throws ExecutionException, InterruptedException {
        log.info("double");
        doIt(2);
    }

    private void doIt(int userId) throws ExecutionException, InterruptedException {
        runTasks(countThreads, executorService, createCallable(rep / countThreads, () -> bonusService.chooseBonuse(1, userId).getValue()));

        addAllResults(countThreads, executorService);
        executor.shutdown();

        checkResult(userId);

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

    private void checkResult(int userId) {
        double sumChance = bonusService.getAllBonuses(userId).stream().mapToDouble(BonusModel::getChance).sum();
        double delta = 0.5 * rep;
        NavigableSet<Double> realDelta = new TreeSet<>();
        bonusService.getAllBonuses(userId).forEach(bm -> {
            double expected = rep * bm.getChance() / sumChance;
            double actual = countMap.get(bm.getValue() );

            assertEquals(expected, actual, delta);
            realDelta.add(Math.abs(actual - expected));
        });
        log.info("Delta: " + realDelta.first() / rep + " - " + realDelta.last() / rep);
    }

    private void showResult() {
        countMap.forEach((key, value) -> {
            log.info(key + " " + 100.0 * value / rep);
        });
    }

}

;