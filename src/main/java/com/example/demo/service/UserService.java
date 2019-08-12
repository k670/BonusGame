package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private BonusService bonusService;

    @Autowired
    public UserService(UserRepository userRepository, BonusService bonusService) {
        this.userRepository = userRepository;
        this.bonusService = bonusService;
    }

    public Collection<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel updete(int uId) {
        Optional<UserModel> optionalUserModel = userRepository.findById(uId);
        if (!optionalUserModel.isPresent()) throw new NullPointerException();

        UserModel userModel = optionalUserModel.get();
        userModel.setCoins(bonusService.chooseBonuse() * userModel.getCoins());
        userRepository.save(userModel);
        return userModel;
    }

   /* @PostConstruct
    private void conectToDB() {
        if (userRepository.count() == 0) {
            ArrayList<UserModel> userModels = new ArrayList<>();
            userModels.add(new UserModel(1, 1));
            userModels.add(new UserModel(2, 10));
            userRepository.saveAll(userModels);
        }
    }*/
}
