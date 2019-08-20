package com.example.demo.service;

import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.CacheEvict;
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

    private ArrayList<UserModel> userModels;

    @Before
    public void beforeEach() {
        userModels = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            userModels.add(new UserModel(i, i));
        }
        when(userRepository.findAll()).thenReturn(userModels);
        when(userRepository.count()).thenReturn(Long.valueOf(userModels.size()));
    }


    @Test
    public void shouldGetAllUsersUponRepoChange() {
        assertArrayEquals(userModels.toArray(), userService.getAllUsers().toArray());

        when(userRepository.findAll()).thenReturn(new ArrayList<UserModel>());
        when(userRepository.count()).thenReturn(0L);
        assertArrayEquals(userModels.toArray(), userService.getAllUsers().toArray());
    }

    @Test
    @CacheEvict("allUsers")
    public void shouldGetEmptyUponResetCache() {
        //reset
        when(userRepository.findById(userModels.get(0).getId())).thenReturn(Optional.ofNullable(userModels.get(0)));
        userService.addDeltaToUserCoins(userModels.get(0).getId(), 0);
        //
        ArrayList<UserModel> emptyArray = new ArrayList<UserModel>();
        when(userRepository.findAll()).thenReturn(emptyArray);
        when(userRepository.count()).thenReturn(0L);

        assertArrayEquals(emptyArray.toArray(), userService.getAllUsers().toArray());
    }

    @Test
    public void shouldUpdateUser() {

        for (UserModel userModel : userModels) {
            when(userRepository.findById(userModel.getId())).thenReturn(Optional.of(userModel));
            when(userRepository.save(userModel)).thenReturn(userModel);
            userModel.setCoins(2 * userModel.getCoins());
            when(userRepository.save(userModel)).thenReturn(userModel);
        }

        for (UserModel userModel : userModels) {
            UserModel newUserModel = userService.addDeltaToUserCoins(userModel.getId(), userModel.getCoins());
            userModel.setCoins(2 * userModel.getCoins());
            assertEquals(userModel, newUserModel);
        }
    }

}
