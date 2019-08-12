package com.example.demo.service;

import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

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
        when(userRepository.count()).thenReturn(new Long(userModels.size()));
        when(userRepository.save(userModels.get(0))).thenReturn(userModels.get(0));
        when(bonusService.chooseBonuse()).thenReturn(2);
    }

    @Test
    public void shouldGetAllUsers() {
        Assert.assertArrayEquals(userModels.toArray(), userService.getAllUsers().toArray());
    }

    @Test
    public void shouldGetEmpty() {
        when(userRepository.findAll()).thenReturn(userModels);
        Assert.assertArrayEquals(userModels.toArray(), userService.getAllUsers().toArray());
    }

    @Test
    public void shouldMultCoins2(){
        Assert.assertEquals(userModels.get(0).getCoins()*2,userService.updete(1).getCoins());
    }
}
