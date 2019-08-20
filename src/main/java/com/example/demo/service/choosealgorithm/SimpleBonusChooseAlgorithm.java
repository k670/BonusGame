package com.example.demo.service.choosealgorithm;

import com.example.demo.model.BonusModel;
import com.example.demo.repository.BonusCachedRepository;
import com.example.demo.service.dtoApi.BonusWinModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Random;

@Component
public class SimpleBonusChooseAlgorithm implements BonusChoosable {

    private static final Random RANDOM = new Random();
    BonusCachedRepository bonusCachedRepository;

    @Autowired
    public SimpleBonusChooseAlgorithm(BonusCachedRepository bonusCachedRepository) {
        this.bonusCachedRepository = bonusCachedRepository;
    }


    @Override
    public BonusModel chooseBonuse(int userId, double bet) {
        return getBonusModel(bet,1);

    }

    BonusModel getBonusModel(double bet, int mult) {
        /*NavigableMap<Double, BonusModel> navigableMap = bonusCachedRepository.getBonusMap(mult);
        if (navigableMap.isEmpty()) {
            throw new RuntimeException();
        }
        Optional<BonusModel> bonusModel = Optional.ofNullable(generateMultipl(navigableMap));
        if (bonusModel.isEmpty()) {
            return null;
        }
        bonusModel.get().setChance(bet * (bonusModel.get().getValue()));
        return bonusModel.get();*/
        return generateMultipl(bonusCachedRepository.getBonusMap(mult).getMap());
    }

    @Override
    public Collection<BonusModel> getAllBonus(int userId) {
        return bonusCachedRepository.getAllBonuses(1);
    }

    @Override
    public BonusChoosableTypes getType() {
        return BonusChoosableTypes.SIMPLECHOOSE;
    }


    private BonusModel generateMultipl(NavigableMap<Double, BonusModel> navigableMap) {
        double chance = navigableMap.lastKey() * RANDOM.nextDouble();
        return navigableMap.higherEntry(chance).getValue();
    }

}

