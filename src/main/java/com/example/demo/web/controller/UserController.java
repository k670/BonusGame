package com.example.demo.web.controller;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<Collection<UserModel>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

/*    @PostMapping("/user")
    public ResponseEntity<UserModel> chooseBonus(@RequestBody BonusActionRequest bet) {
        if(bet.getBet()<=0){
            return ResponseEntity.badRequest().header("Problem","Wrong bet").build();
        }
        UserModel userModel;
        try {
            userModel = userService.userGetBonus(bet.getUserId(), bet.getBet());
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().header("Problem", ex.getMessage()).build();
        }
        if (userModel == null) {
            return ResponseEntity.status(500).header("Problem", "Your coins less than bet").build();
        } else {
            return ResponseEntity.ok(userModel);
        }
    }*/


    @ExceptionHandler(Throwable.class)
    public ResponseEntity handle(Throwable throwable) {
        log.error(throwable.getMessage());
        return ResponseEntity.status(500).header("Problem", "I don't know").build();
    }
}
