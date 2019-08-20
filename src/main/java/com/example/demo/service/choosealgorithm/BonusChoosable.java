package com.example.demo.service.choosealgorithm;

import com.example.demo.model.BonusModel;

import java.util.Collection;

public interface BonusChoosable {
    BonusModel chooseBonuse(int userId, double bet);

    Collection<BonusModel> getAllBonus(int userId);

    BonusChoosableTypes getType();
}
