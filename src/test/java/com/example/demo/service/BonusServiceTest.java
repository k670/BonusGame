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
import java.util.Optional;

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


    private ArrayList<BonusModel> bonusModels;

    @Before
    public void before() {
        initArray();
        when(bonusRepository.findAll()).thenReturn(bonusModels);
        when(bonusRepository.count()).thenReturn(Long.valueOf(bonusModels.size()));
        bonusService.readDB();
    }


    @Test
    public void shouldReturnCollectionUponGetAll() {
        int size = bonusModels.size() - 1;
        int i = 0;
        while (size>0){
            if (bonusModels.get(i).getValue() == bonusModels.get(i + 1).getValue()) {
                bonusModels.remove(i);
            }else {
                i++;
            }
            size--;
        }

        assertArrayEquals(bonusModels.toArray(), bonusService.getAllBonuses().toArray());
        initArray();
    }

    @Test
    public void shouldReturnEnptyCollectionUponGetFromEmptyDB() {

        when(bonusRepository.findAll()).thenReturn(new ArrayList<>());
        bonusService.readDB();

        assertArrayEquals((new ArrayList<BonusModel>()).toArray(), bonusService.getAllBonuses().toArray());
    }


    @Test(expected = RuntimeException.class)
    public void shouldThrowNullPointerExceptionUponChooseBonusFromEmptyDB() {
        when(bonusRepository.findAll()).thenReturn(new ArrayList<>());
        bonusService.readDB();
        assertNull(bonusService.chooseBonuse());
    }

    @Test
    public void shouldReturn2UponChooseBonus() {

        assertEquals(Optional.of(bonusModels.get(bonusModels.size() - 1)), Optional.ofNullable(bonusService.chooseBonuse()));
    }

    private void initArray() {
        bonusModels = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            bonusModels.add(new BonusModel(i, "mx" + i, 2, i));
        }
    }

}
