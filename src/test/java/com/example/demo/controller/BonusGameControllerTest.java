package com.example.demo.controller;

import com.example.demo.model.BonusModel;
import com.example.demo.service.BonusService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BonusGameControllerTest {


    @Autowired
    private MockMvc bonusControllerMock;

    private ArrayList<BonusModel> bonusModels;

    private void  initBonusModel(){
        bonusModels = new ArrayList<>();
        for (int i = 1; i < 5; i++) {

            bonusModels.add(new BonusModel(i, (new Random()).nextInt()));
        }
    }
    private void setBonusControllerMock(ArrayList<BonusModel> bonusModels){
        BonusService bonusService = Mockito.mock(BonusService.class);
        Mockito.when(bonusService.getAllBonuses()).thenReturn(bonusModels);
        Mockito.when(bonusService.chooseBonuse()).thenReturn(bonusModels.get(0));
        bonusControllerMock = MockMvcBuilders.standaloneSetup(new BonusGameController(bonusService)).build();
    }

    private void setEmptyModelToBonusControllerMock(){
        BonusService bonusService = Mockito.mock(BonusService.class);
        Mockito.when(bonusService.chooseBonuse()).thenThrow(NullPointerException.class);
        Mockito.when(bonusService.getAllBonuses()).thenReturn(new ArrayList<>());
        bonusControllerMock = MockMvcBuilders.standaloneSetup(new BonusGameController(bonusService)).build();
    }

    @Before
    public void before() {
        initBonusModel();
        setBonusControllerMock(bonusModels);
    }

    @Test
    public void getAllBonusesStatus() throws Exception {
        bonusControllerMock.perform(get("/bonus")).andExpect(status().isOk());
    }

    @Test
    public void getAllBonusesEmpty() throws Exception {
        setEmptyModelToBonusControllerMock();
        bonusControllerMock.perform(get("/bonus")).andExpect(content().string("[]"));
        setBonusControllerMock(bonusModels);
    }

    @Test
    public void getAllBonusesContent() throws Exception {
        String bonusModelsString = bonusModels.toString().replaceAll(", ",",");
        bonusControllerMock.perform(get("/bonus")).andExpect(content().string(bonusModelsString));

    }


    @Test
    public void chooseBonuseStatus() throws Exception {
        bonusControllerMock.perform(post("/bonus")).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void chooseBonuseEmpty() throws Exception {
        setEmptyModelToBonusControllerMock();
        bonusControllerMock.perform(post("/bonus")).andExpect(status().is(500));
        setBonusControllerMock(bonusModels);
    }

    @Test
    public void chooseBonuseContent() throws Exception {
        String bonusModelsString = bonusModels.get(0).toString().replaceAll(", ",",");
        bonusControllerMock.perform(post("/bonus")).andExpect(content().string(bonusModelsString));

    }

}