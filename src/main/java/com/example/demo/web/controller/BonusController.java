package com.example.demo.web.controller;

import com.example.demo.model.BonusModel;
import com.example.demo.service.BonusService;
import com.example.demo.web.entity.BonusActionRequest;
import com.example.demo.web.entity.BonusActionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Collection<BonusModel>> getAllBonuses(@RequestParam int userId) {
        if (userId < 1) {
            throw new RuntimeException("userId<1");
        }
        Collection<BonusModel> bonusModelCollection = bonusService.getAllBonuses(userId);
        return ResponseEntity.ok().body(bonusModelCollection);
    }

    @PostMapping("/bonus")
    public ResponseEntity<BonusActionResponse> chooseBonus(@RequestBody BonusActionRequest bonusActionRequest) {
        BonusModel bonusModel = bonusService.chooseBonuse(bonusActionRequest.getBet(), bonusActionRequest.getUserId());
        return ResponseEntity.ok().body(BonusActionResponse.builder().name(bonusModel.getName()).value(bonusModel.getValue()).win(bonusModel.getChance()).build());

    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity handle(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(500).header("Problem", throwable.getMessage()).build();
    }
}
