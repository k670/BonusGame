package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BonusService bonusService;

    private ArrayList<UserModel> userModels;

    @Before
    public void beforeEach() {
        userModels = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            userModels.add(new UserModel(i, i));
        }
        when(userRepository.findAll()).thenReturn(userModels);
        when(userRepository.findById(1)).thenReturn(Optional.of(userModels.get(0)));
        when(userRepository.count()).thenReturn(Long.valueOf(userModels.size()));
        when(userRepository.save(userModels.get(0))).thenReturn(userModels.get(0));
    }

    @Test
    public void shouldGetAllUsers() {
        assertArrayEquals(userModels.toArray(), userService.getAllUsers().toArray());
    }

    @Test
    public void shouldGetEmpty() {
        when(userRepository.findAll()).thenReturn(userModels);
        assertArrayEquals(userModels.toArray(), userService.getAllUsers().toArray());
    }

    @Test
    public void shouldMultCoins2() {
        /*when(bonusService.chooseBonuse(userModels.get(1).getCoins(), 1))
                .thenReturn(new BonusModel(1, "mx2", 2.0, 2 * userModels.get(1).getCoins()));//new BonusActionResponse( "mx1",2.0,100.0));*/
        double expect = userModels.get(0).getCoins() * 2;
        double actual = userService.updete(1, expect).getCoins();
        assertEquals(expect, actual, 0.01);
    }
}
