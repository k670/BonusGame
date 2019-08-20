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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable("allUsers")
    public Collection<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @CacheEvict(value = "allUsers", allEntries = true)
    public UserModel addDeltaToUserCoins(int uId, double deltaCoins) {
        Optional<UserModel> optionalUserModel = userRepository.findById(uId);
        UserModel userModel = optionalUserModel.orElseThrow(() -> new RuntimeException("User don't found"));
        userModel.setCoins(deltaCoins + userModel.getCoins());
        userRepository.save(userModel);
        return userModel;
    }

}
