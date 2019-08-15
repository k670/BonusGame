package com.example.demo.service;

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
        return userRepository.findAll();
    }

    @CacheEvict(value = "allUsers")
    public UserModel updete(int uId, double coins) {
        Optional<UserModel> optionalUserModel = userRepository.findById(uId);
        UserModel userModel = optionalUserModel.orElseThrow(NullPointerException::new);
        userModel.setCoins(coins);
        userRepository.save(userModel);
        return userModel;
    }

    @CacheEvict(value = "allUsers")
    public UserModel userGetBonus(int userId, double bet) {

        Optional<UserModel> optionalUserModel = userRepository.findById(userId);
        UserModel userModel = optionalUserModel.orElseThrow(NullPointerException::new);
        double coins = userModel.getCoins();
        if (coins < bet) {
            return null;
        }
        coins = coins - bet + bonusService.chooseBonuse(bet, userId).getChance();
        userModel.setCoins(coins);
        userRepository.save(userModel);
        return userModel;

    }

}
