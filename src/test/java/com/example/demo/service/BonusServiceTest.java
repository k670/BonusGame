package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.repository.BonusCachedRepository;
import com.example.demo.repository.dtoRepository.SearchMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.UUID;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BonusServiceTest {


    @Autowired
    BonusService bonusService;

    @MockBean
    BonusCachedRepository bonusCachedRepository;

    private ArrayList<BonusModel> bonusModels;

    private int maxValidId = Integer.MAX_VALUE;
    private int minValidId = 1;
    private int maxNotValidId = 0;
    private int minNotValidId = Integer.MIN_VALUE;
    private double minValidBet = Double.MIN_VALUE;
    private double maxValidBet = Double.MAX_VALUE;
    private double minNotValidBet = 0;
    private double maxNotValidBet = 0 - Double.MAX_VALUE;


    @Before
    public void before() {
        initArray();
        mockRepo(bonusModels);
        bonusService.readBonusRepo();
    }

    //Get
    @Test
    public void shouldReturnCollectionUponGetWithValidId() {
        mockRepo(bonusModels);
        checkGetAll(1);
        checkGetAll(2);
        checkGetAll(2);
    }


    private void checkGetAll(int mult) {
        Collection<BonusModel> minCollection = bonusService.getAllBonuses(minValidId + mult / 2);
        Collection<BonusModel> maxCollection = bonusService.getAllBonuses(maxValidId - mult / 2);
        assertEquals(bonusModels.size(), minCollection.size());
        assertEquals(bonusModels.size(), maxCollection.size());
        bonusModels.forEach(bonusModel -> {
            bonusModel.setValue(mult * bonusModel.getValue());
            assertTrue(maxCollection.contains(bonusModel));
            assertTrue(minCollection.contains(bonusModel));
        });
    }


    @Test
    public void shouldReturnEmptyCollectionUponGetFromEmptyRepo() {
        mockRepo(new ArrayList<>());

        assertArrayEquals((new ArrayList<BonusModel>()).toArray(), bonusService.getAllBonuses(1).toArray());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionUponGetIdIsNotValid() {
        assertNull(bonusService.getAllBonuses(minNotValidId));
        assertNull(bonusService.getAllBonuses(maxNotValidId));
    }

    //Post
    @Test//Delta in chance
    public void shouldReturnBonusUponValidIdAndBet() {
        ArrayList<BonusModel> bonusModelArrayList = new ArrayList<>();
        BonusModel exp = new BonusModel(1, UUID.randomUUID().toString(), 1, 1);
        bonusModelArrayList.add(exp);
        mockRepo(bonusModelArrayList);


        exp.setChance(minValidBet);
        assertEquals(bonusModelArrayList.get(0), bonusService.chooseBonuse(minValidBet, minValidId));
        assertEquals(bonusModelArrayList.get(0), bonusService.chooseBonuse(minValidBet, maxValidId));
        exp.setChance(maxValidBet);
        assertEquals(bonusModelArrayList.get(0), bonusService.chooseBonuse(maxValidBet, minValidId));
        assertEquals(bonusModelArrayList.get(0), bonusService.chooseBonuse(maxValidBet, maxValidId));

        exp.setValue(2 * minValidBet);
        assertEquals(bonusModelArrayList.get(0), bonusService.chooseBonuse(minValidBet, minValidId));
        assertEquals(bonusModelArrayList.get(0), bonusService.chooseBonuse(minValidBet, maxValidId));


    }

    private void mockRepo(ArrayList<BonusModel> bonusModelArrayList) {
        when(bonusCachedRepository.getAllBonuses(1)).thenReturn(bonusModelArrayList);
        when(bonusCachedRepository.getAllBonuses(2)).thenReturn(bonusModelArrayList);
        NavigableMap<Double, BonusModel> navigableMap = new TreeMap<>();
        if (bonusModelArrayList != null && bonusModelArrayList.size() > 0) {
            navigableMap.put(bonusModelArrayList.get(0).getValue(), bonusModelArrayList.get(0));
            for (int i = 1; i < bonusModelArrayList.size(); i++) {
                navigableMap.put(bonusModelArrayList.get(i).getChance() + navigableMap.lastKey(), bonusModelArrayList.get(i));
            }
        }
        when(bonusCachedRepository.getBonusMap(1)).thenReturn(SearchMap.builder().map(navigableMap).build());
        when(bonusCachedRepository.getBonusMap(2)).thenReturn(SearchMap.builder().map(navigableMap).build());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowNullPointerExceptionUponChooseBonusFromEmptyDB() {
        mockRepo(new ArrayList<>());
        assertNull(bonusService.chooseBonuse(bonusModels.get(0).getValue(), bonusModels.get(0).getId()));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionUponPostIdIsNotValid() {
        assertNull(bonusService.chooseBonuse(minValidBet, minNotValidId));
        assertNull(bonusService.chooseBonuse(maxValidBet, minNotValidId));
        assertNull(bonusService.chooseBonuse(minValidBet, maxNotValidId));
        assertNull(bonusService.chooseBonuse(maxValidBet, maxNotValidId));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionUponBetIsNotValid() {
        assertNull(bonusService.chooseBonuse(minNotValidBet, minValidId));
        assertNull(bonusService.chooseBonuse(maxNotValidBet, minValidId));
        assertNull(bonusService.chooseBonuse(minNotValidBet, maxValidId));
        assertNull(bonusService.chooseBonuse(maxNotValidBet, maxValidId));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionUponBetAndIdIsNotValid() {
        assertNull(bonusService.chooseBonuse(minNotValidBet, minNotValidId));
        assertNull(bonusService.chooseBonuse(maxNotValidBet, minNotValidId));
        assertNull(bonusService.chooseBonuse(minNotValidBet, maxNotValidId));
        assertNull(bonusService.chooseBonuse(maxNotValidBet, maxNotValidId));
    }


    private void initArray() {
        bonusModels = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            int j = Integer.MAX_VALUE - i - 1;
            bonusModels.add(new BonusModel(i, UUID.randomUUID().toString(), i, i));
            bonusModels.add(new BonusModel(j, UUID.randomUUID().toString(), j, j));
        }
    }
}
