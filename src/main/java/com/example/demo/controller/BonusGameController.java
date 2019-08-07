package com.example.demo.controller;

import com.example.demo.model.BonusModel;
import com.example.demo.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class BonusGameController {

    private BonusService bonusService;

    @Autowired
    public BonusGameController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @GetMapping(path = "/bonus")
    public Collection<BonusModel> getAllBonuses() {

        return bonusService.getAllBonuses();
    }

    @PostMapping(path = "/bonus")
    public ResponseEntity<BonusModel> chooseBonuse() {
        return ResponseEntity.ok(bonusService.chooseBonuse());

    }
}
