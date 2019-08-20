package com.example.demo.service.choosealgorithm;

import com.example.demo.model.BonusModel;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ExceptionBonusChooseAlgorithm implements BonusChoosable {
    @Override
    public BonusModel chooseBonuse(int userId, double bet) {
        throw new RuntimeException("User id isn't valid");
    }

    @Override
    public Collection<BonusModel> getAllBonus(int userId) {
        throw new RuntimeException("User id isn't valid");
    }

    @Override
    public BonusChoosableTypes getType() {
        return BonusChoosableTypes.EXCEPTIONCHOOSE;
    }
}
