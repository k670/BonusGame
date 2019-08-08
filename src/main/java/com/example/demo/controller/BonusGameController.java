package com.example.demo.controller;

import com.example.demo.model.BonusModel;
import com.example.demo.service.BonusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController("/bonus")
@Slf4j
public class BonusGameController {

    private BonusService bonusService;

    @Autowired
    public BonusGameController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @GetMapping
    public Collection<BonusModel> getAllBonuses() {
        return bonusService.getAllBonuses();
    }

    @PostMapping
    public ResponseEntity<BonusModel> chooseBonuse() {
        BonusModel bonusModel = bonusService.chooseBonuse();
        return ResponseEntity.ok(bonusModel);

    }
}
