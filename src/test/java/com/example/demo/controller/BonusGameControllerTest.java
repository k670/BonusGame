package com.example.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BonusGameControllerTest {

    @Autowired
    BonusGameController bonusGameController;

    @Test
    public void getAllBonuses() {

    }

    @Test
    public void chooseBonuse() {
        Map<String ,Integer> bonus = bonusGameController.chooseBonuse();
        assertEquals(1,bonus.get("value")-bonus.get("index"));
    }
}