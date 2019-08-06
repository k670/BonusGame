package com.example.demo.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

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