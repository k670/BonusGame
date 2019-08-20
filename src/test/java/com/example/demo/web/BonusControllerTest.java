package com.example.demo.web;

import com.example.demo.model.BonusModel;
import com.example.demo.service.BonusService;
import com.example.demo.web.entity.BonusActionRequest;
import com.example.demo.web.entity.BonusActionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class BonusControllerTest {


    @Autowired
    private MockMvc bonusControllerMock;

    @MockBean
    private BonusService bonusService;

    private ArrayList<BonusModel> bonusModels;

    private ObjectWriter objectWriter;
    private Map<Integer, BonusActionRequest> requestMap;
    private int notExistUserId = Integer.MAX_VALUE >>> 1;

    private Map<Integer, BonusActionResponse> responseMap;


    @Before
    public void beforeEach() {
        initBonusModelAndEntity(10);
        setBonusControllerMock(bonusModels);
        ObjectMapper objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writer();

    }

    //Get
    @Test//Good
    public void shouldShowAllBonusesInJsonWithStatus200UpondGetBonus() {
        bonusModels.forEach(bonusModel -> {
            try {
                bonusControllerMock.perform(get("/bonus?userId=" + bonusModel.getId()))
                        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectWriter.writeValueAsString(bonusModels)));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }

    @Test//EmptyDB
    public void shouldShowBracketsUponGetEmptyTableInDB() throws Exception {
        int id = 1;
        when(bonusService.getAllBonuses(id)).thenReturn(new ArrayList<>());
        bonusControllerMock.perform(get("/bonus?userId=" + id)).andExpect(content().string("[]"));
    }

    @Test(expected = Exception.class)//Bad id
    public void shouldThrowExceptionWithStatus500UpondGetBonusNotExistUserIdProp() throws Exception {
        bonusControllerMock.perform(get("/bonus?userId=" + notExistUserId)).andExpect(status().is(500));
    }

    //Post

    @Test//Good
    public void shouldShowBonusModelWithStatus200UpondPostBonusWithBodyProp() throws Exception {
        for (BonusModel bonusModel : bonusModels) {
            String body = objectWriter.writeValueAsString(requestMap.get(bonusModel.getId()));
            String res = objectWriter.writeValueAsString(responseMap.get(bonusModel.getId()));
            bonusControllerMock.perform(post("/bonus").contentType(APPLICATION_JSON_UTF8).content(body))
                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andExpect(content().string(res));
        }
    }

    @Test//without body
    public void shouldReturnEmptyWithStatus500UponPostWithoutBody() throws Exception {
        bonusControllerMock.perform(post("/bonus")).andExpect(status().is(500));
    }

    @Test(expected = Exception.class)//bad id
    public void shouldReturnEmptyWithStatus500UponPostNotExistUserIdProp() throws Exception {
        bonusControllerMock.perform(post("/bonus").contentType(APPLICATION_JSON_UTF8)
                .content(objectWriter.writeValueAsString(new BonusActionRequest(notExistUserId, notExistUserId))))
                .andExpect(status().is(500));
    }


    @Test(expected = Exception.class)//bad bet
    public void shouldReturnEmptyWithStatus500UponPostWrongBetProp() throws Exception {
        BonusActionRequest lastWrongBet = new BonusActionRequest(0 - Double.MAX_VALUE, Integer.MIN_VALUE);
        BonusActionRequest firstWrongBet = new BonusActionRequest(0, 0);
        bonusControllerMock.perform(post("/bonus")
                .contentType(APPLICATION_JSON_UTF8).content(objectWriter.writeValueAsString(firstWrongBet)))
                .andExpect(status().is(500));
        bonusControllerMock.perform(post("/bonus")
                .contentType(APPLICATION_JSON_UTF8).content(objectWriter.writeValueAsString(lastWrongBet)))
                .andExpect(status().is(500));
    }


    private void initBonusModelAndEntity(int sizeHalf) {
        sizeHalf++;
        bonusModels = new ArrayList<>();
        requestMap = new HashMap<>();
        responseMap = new HashMap<>();
        for (int i = 1; i < sizeHalf; i++) {
            int j = Integer.MAX_VALUE - i;

            String nameI = UUID.randomUUID().toString();
            String nameJ = UUID.randomUUID().toString();
            bonusModels.add(new BonusModel(i, nameI, i, i));
            bonusModels.add(new BonusModel(j, nameJ, j, j));

            requestMap.put(i, new BonusActionRequest(j, i));
            requestMap.put(j, new BonusActionRequest(i, j));

            responseMap.put(i, BonusActionResponse.builder().name(nameI).value(i).win(i).build());
            responseMap.put(j, BonusActionResponse.builder().name(nameJ).value(j).win(j).build());
        }

    }

    private void setBonusControllerMock(ArrayList<BonusModel> bonusModels) {
        bonusModels.forEach(bonusModel -> {
            when(bonusService.getAllBonuses(bonusModel.getId())).thenReturn(bonusModels);
            when(bonusService.chooseBonuse(requestMap.get(bonusModel.getId()).getBet(), bonusModel.getId())).thenReturn(bonusModel);
            when(bonusService.chooseBonuse(requestMap.get(bonusModel.getId()).getBet(), notExistUserId)).thenThrow(NullPointerException.class);
        });
        when(bonusService.getAllBonuses(notExistUserId)).thenThrow(RuntimeException.class);
    }
}
