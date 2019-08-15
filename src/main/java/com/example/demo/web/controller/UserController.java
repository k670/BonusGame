package com.example.demo.web.controller;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/user")
    public ResponseEntity<UserModel> chooseBonuse(@RequestParam int id, @RequestParam double bet) {
        UserModel userModel = userService.userGetBonus(id, bet);
        if(userModel==null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(userModel);
        }
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity handle(Throwable throwable) {
        log.error(throwable.getMessage());
        return ResponseEntity.status(500).build();
    }
}
