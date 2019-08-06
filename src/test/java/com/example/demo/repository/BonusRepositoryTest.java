package com.example.demo.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BonusRepositoryTest {

    @Autowired
    BonusRepository bonusRepository;


    @Test
    public void getBonusesSize() {
        assertEquals(4,bonusRepository.getBonuses().size());
    }

    @Test
    public void getBonuses() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        assertArrayEquals(arrayList.toArray(),bonusRepository.getBonuses().toArray());
    }

    @Test
    public void getOneBonuse() {
        assertEquals(1,bonusRepository.getOneBonuse(0));
    }
}