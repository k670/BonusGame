package com.example.demo.controller;

import com.example.demo.model.BonusModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.BonusService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BonusControllerTest {


    @Autowired
    private MockMvc bonusControllerMock;

    @MockBean
    private BonusService bonusService;

    private ArrayList<BonusModel> bonusModels;


    @Before
    public void beforeEach() {
        initBonusModelAndUser();
        setBonusControllerMock(bonusModels);

    }

    @Test
    public void shouldShowBracketsUponGetEmptyTableInDB() throws Exception {
        when(bonusService.getAllBonuses()).thenReturn(new ArrayList<>());

        bonusControllerMock.perform(get("/bonus")).andExpect(content().string("[]"));
    }

    @Test
    public void shouldShowAllBonusesInJsonWithStatus200UpondGetBonus() throws Exception {

        String body = bonusControllerMock.perform(get("/bonus"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        bonusModels.forEach(bonusModel -> {
            String bonusModelsString = "{\"id\":" + bonusModel.getId() + ",\"value\":" + bonusModel.getValue() + ",\"chance\":" + bonusModel.getChance() + "}";
            assertTrue(body.contains(bonusModelsString));
        });

    }

    @Test
    public void shouldReturnEmptyWithStatus500UponPostNotExistUserIdProp() throws Exception {

        when(bonusService.chooseBonuse()).thenThrow(NullPointerException.class);

        bonusControllerMock.perform(post("/bonus")).andExpect(status().is(500));
    }
/* to userContr
    @Test
    public void shouldReturnEmptyWithStatus500UponPostWioutIdProp() throws Exception {
        bonusControllerMock.perform(post("/bonus")).andExpect(status().is(500));
    }*/


    @Test
    public void shouldShowNumberWithStatus200UpondPostBonusWithIdProp() throws Exception {
        bonusControllerMock.perform(post("/bonus"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }


    private void initBonusModelAndUser() {
        bonusModels = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            bonusModels.add(new BonusModel(i, (new Random()).nextInt(), i * 10));
        }
    }


    private void setBonusControllerMock(ArrayList<BonusModel> bonusModels) {
        when(bonusService.getAllBonuses()).thenReturn(bonusModels);
        when(bonusService.chooseBonuse()).thenReturn(2);
    }
}