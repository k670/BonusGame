package com.example.demo.web;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    private MockMvc userControllerMock;

    @MockBean
    private UserService userService;

    private ArrayList<UserModel> userModels;


    @Before
    public void beforeEach() {
        initUserModel();
        setUserControllerMock(userModels);
    }

    //Get
    @Test
    public void shouldShowBracketsUponGetEmptyTableInDB() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());

        userControllerMock.perform(get("/user")).andExpect(content().string("[]"));
    }

    @Test
    public void shouldShowAllUsersInJsonWithStatus200UpondGetBonus() throws Exception {

        String body = userControllerMock.perform(get("/user"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        userModels.forEach(userModel -> {
            String userModelsString = "{\"id\":" + userModel.getId() + ",\"coins\":" + userModel.getCoins() + "}";
            assertTrue(body.contains(userModelsString));
        });
    }

    private void initUserModel() {
        userModels = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            userModels.add(new UserModel(i, (new Random()).nextInt()));
        }
    }

    private void setUserControllerMock(ArrayList<UserModel> userModels) {
        when(userService.getAllUsers()).thenReturn(userModels);
    }
}