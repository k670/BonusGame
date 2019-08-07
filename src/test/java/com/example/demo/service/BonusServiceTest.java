package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.repository.BonusRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BonusServiceTest {

    @Autowired
    BonusService bonusService;

    private Collection<BonusModel> bonusModels;

    private void setBonusModelsToService(Collection<BonusModel> bonusModels) throws NoSuchFieldException, IllegalAccessException {

        BonusRepository mockBonusRepository = Mockito.mock(BonusRepository.class);
        Mockito.when(mockBonusRepository.count()).thenReturn((long) bonusModels.size());
        Mockito.when(mockBonusRepository.findAll()).thenReturn(bonusModels);

        Field bonusServiceField = BonusService.class.getDeclaredField("bonusRepository");
        bonusServiceField.setAccessible(true);
        bonusServiceField.set(bonusService, mockBonusRepository);
    }

    @Before
    public void beforeEach() throws NoSuchFieldException, IllegalAccessException {


        bonusModels = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            bonusModels.add(new BonusModel(i, random.nextInt()));

        }

        setBonusModelsToService(bonusModels);

    }

    @Test
    public void getAllBonusesSize() {
        assertEquals(bonusModels.size(), bonusService.getAllBonuses().size());
    }

    @Test
    public void getAllBonuses() {
        assertArrayEquals(bonusModels.toArray(), bonusService.getAllBonuses().toArray());
    }

    @Test
    public void chooseBonuse() {
        BonusModel bonus = bonusService.chooseBonuse();
        assertTrue(bonusModels.contains(new BonusModel(bonus.getId(), bonus.getValue())));
    }

    @Test(expected = NullPointerException.class)
    public void chooseNullBonuse() throws NoSuchFieldException, IllegalAccessException {
        setBonusModelsToService(new ArrayList<BonusModel>());
        bonusService.chooseBonuse();

    }
}