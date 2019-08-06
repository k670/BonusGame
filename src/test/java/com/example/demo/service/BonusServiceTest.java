package com.example.demo.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class BonusServiceTest {

    @Autowired
    BonusService bonusService;
    @Test
    public void getAllBonuses() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        assertArrayEquals(arrayList.toArray(),bonusService.getAllBonuses().toArray());
    }

    @Test
    public void chooseBonuse() {
        Map<String ,Integer> bonus = bonusService.chooseBonuse();
        assertEquals(1,bonus.get("value")-bonus.get("index"));
    }
}