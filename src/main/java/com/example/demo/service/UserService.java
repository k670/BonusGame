package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@EnableCaching
public class UserService {

    private UserRepository userRepository;
    private BonusService bonusService;

    @Autowired
    public UserService(UserRepository userRepository, BonusService bonusService) {
        this.userRepository = userRepository;
        this.bonusService = bonusService;
    }
    @Cacheable("allUsers")
    public Collection<UserModel> getAllUsers() {
/*        for (int i = 0; i < 100; i++) {

             userRepository.findAll();
        }*/
        return  userRepository.findAll();
    }

    @CacheEvict(value = "allUsers", allEntries = true)
    public UserModel updete(int uId) {
        Optional<UserModel> optionalUserModel = userRepository.findById(uId);
        if (!optionalUserModel.isPresent()) throw new NullPointerException();

        UserModel userModel = optionalUserModel.get();
        userModel.setCoins(bonusService.chooseBonuse() * userModel.getCoins());
        userRepository.save(userModel);
        return userModel;
    }

}
