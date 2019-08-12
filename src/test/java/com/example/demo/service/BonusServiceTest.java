
package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.repository.BonusRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BonusServiceTest {

    @Autowired
    BonusService bonusService;

    @MockBean
     BonusRepository bonusRepository;


    private  ArrayList<BonusModel> bonusModels;

    @Before
    public  void before() throws NoSuchFieldException, IllegalAccessException {
        bonusModels = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i < 5; i++) {
            bonusModels.add(new BonusModel(i, 2, i * 10));
        }


        when(bonusRepository.findAll()).thenReturn(bonusModels);
        when(bonusRepository.count()).thenReturn(new Long(bonusModels.size()));
        bonusService.createPercentMap();
    }


    @Test
    public void shouldReturnCollectionUponGetAll() {
        assertArrayEquals(bonusModels.toArray(), bonusService.getAllBonuses().toArray());
    }

    @Test
    public void shouldReturnEnptyCollectionUponGetFromEmptyDB() {

        when(bonusRepository.findAll()).thenReturn(new ArrayList<>());

        assertArrayEquals((new ArrayList<BonusModel>()).toArray(), bonusService.getAllBonuses().toArray());
    }


    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionUponChooseBonusFromEmptyDB() {
        when(bonusRepository.findAll()).thenReturn(new ArrayList<>());
        bonusService.createPercentMap();
        assertNull(bonusService.chooseBonuse());
    }

    @Test
    public void shouldReturn2UponChooseBonus() {
        assertEquals(java.util.Optional.of(2), Optional.ofNullable(bonusService.chooseBonuse()));
    }



}
