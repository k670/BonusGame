/*
package com.example.demo.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BonusRepositoryTest {


    @Autowired
   private BonusRepository bonusRepository;

    @Before
    public void beforeEach() throws NoSuchFieldException, IllegalAccessException {
*/
/*        ArrayList<Integer> bonuses;
        Field bonusesField = BonusRepository.class.getDeclaredField("bonuses");
        bonusesField.setAccessible(true);
        bonuses = (ArrayList<Integer>)bonusesField.get(bonusRepository);
        bonuses.clear();
        bonuses.add(8);*//*

    }

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
}*/
