package com.example.demo.controller;

import com.example.demo.model.BonusModel;
import com.example.demo.service.BonusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Slf4j
public class BonusController {

    private final BonusService bonusService;

    @Autowired
    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @GetMapping("/bonus")
    public ResponseEntity<Collection<BonusModel>> getAllBonuses() {
        return ResponseEntity.ok(bonusService.getAllBonuses());
    }

    @PostMapping("/bonus")
    public ResponseEntity<Integer> chooseBonuse() {
        return ResponseEntity.ok(bonusService.chooseBonuse());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity handle(Throwable throwable) {
        log.error(throwable.getMessage());
        return ResponseEntity.status(500).build();
    }
}
