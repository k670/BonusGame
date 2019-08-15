package com.example.demo.web;

import com.example.demo.model.BonusModel;
import com.example.demo.service.BonusService;
import com.example.demo.web.entity.BonusActionRequest;
import com.example.demo.web.entity.BonusActionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
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

    private ObjectWriter objectWriter;

    private BonusActionResponse bonusActionResponse;

    @Before
    public void beforeEach() {
        initBonusModelAndUser();
        setBonusControllerMock(bonusModels);
        ObjectMapper objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writer();
        bonusActionResponse = new BonusActionResponse(bonusModels.get(0).getName(), bonusModels.get(0).getValue(), bonusModels.get(0).getValue());

    }

    @Test
    public void shouldShowBracketsUponGetEmptyTableInDB() throws Exception {
        when(bonusService.getAllBonuses()).thenReturn(new ArrayList<>());

        bonusControllerMock.perform(get("/bonus")).andExpect(content().string("[]"));
    }

    @Test
    public void shouldShowAllBonusesInJsonWithStatus200UpondGetBonus() throws Exception {

        String body = bonusControllerMock.perform(get("/bonus"))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        bonusModels.forEach(bonusModel -> {
            String bonusModelsString = "{\"name\":\"" + bonusModel.getName() + "\",\"value\":" + bonusModel.getValue() + "}";
            assertTrue(body.contains(bonusModelsString));
        });

    }

    @Test
    public void shouldReturnEmptyWithStatus500UponPostNotExistUserIdProp() throws Exception {

        when(bonusService.chooseBonuse()).thenThrow(NullPointerException.class);

        bonusControllerMock.perform(post("/bonus")).andExpect(status().is(500));
    }


    @Test
    public void shouldShowBonusModelWithStatus200UpondPostBonusWithIdProp() throws Exception {
        when(bonusService.chooseBonuse(1.0,1)).thenReturn(bonusModels.get(0));
//
        BonusActionRequest bonusActionRequest = new BonusActionRequest(1.0,1);
        String body = objectWriter.writeValueAsString(bonusActionRequest);
        String res = objectWriter.writeValueAsString(bonusActionResponse);
        bonusControllerMock.perform(post("/bonus").contentType(APPLICATION_JSON_UTF8).content(body))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(res));
    }


    private void initBonusModelAndUser() {
        bonusModels = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            bonusModels.add(new BonusModel(i, "mx" + i, i, i));
        }
    }


    private void setBonusControllerMock(ArrayList<BonusModel> bonusModels) {
        when(bonusService.getAllBonuses()).thenReturn(bonusModels);
        when(bonusService.chooseBonuse()).thenReturn(bonusModels.get(0));
        when(bonusService.chooseBonuse(1.0,1)).thenReturn(bonusModels.get(0));
        /*when(bonusService.chooseBonuse()).thenReturn(bonusActionResponse);
        when(bonusService.chooseBonuse(new BonusActionRequest(1.0))).thenReturn(bonusActionResponse);*/
    }
}
