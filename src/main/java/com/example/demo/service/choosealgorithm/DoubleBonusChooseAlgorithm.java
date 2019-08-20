/*
package com.example.demo.service.choosealgorithm;

import com.example.demo.model.BonusModel;
import com.example.demo.repository.BonusCachedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class DoubleBonusChooseAlgorithm extends SimpleBonusChooseAlgorithm  {

    private final int mult = 2;

    @Autowired
    public DoubleBonusChooseAlgorithm(BonusCachedRepository bonusCachedRepository) {
        super(bonusCachedRepository);
        //this.bonusCachedRepository = bonusCachedRepository;
    }

    @Override
    public BonusModel chooseBonuse(int userId, double bet) {

        return super.getBonusModel(bet, mult);
    }

    @Override
    public Collection<BonusModel> getAllBonus(int userId) {
      */
/*Collection<BonusModel> collection = new ArrayList<>();
        bonusCachedRepository.getAllBonuses(mult).forEach(bm -> {
            BonusModel bonusModel = bm.clone();
            bonusModel.setValue(mult * bonusModel.getValue());
            collection.add(bonusModel);
        });*/
/*

        return bonusCachedRepository.getAllBonuses(mult);
    }

    @Override
    public BonusChoosableTypes getType() {
        return BonusChoosableTypes.DOUBLECHOOSE;
    }

}
*/


package com.example.demo.service.choosealgorithm;

import com.example.demo.model.BonusModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class DoubleBonusChooseAlgorithm implements BonusChoosable {

    private SimpleBonusChooseAlgorithm simpleBonusChooseAlgorithm;
    private final int mult = 2;

    @Autowired
    public DoubleBonusChooseAlgorithm(SimpleBonusChooseAlgorithm simpleBonusChooseAlgorithm) {
        this.simpleBonusChooseAlgorithm = simpleBonusChooseAlgorithm;
    }

    @Override
    public BonusModel chooseBonuse(int userId, double bet) {
        return simpleBonusChooseAlgorithm.getBonusModel(bet, mult);
    }

    @Override
    public Collection<BonusModel> getAllBonus(int userId) {
        return simpleBonusChooseAlgorithm.bonusCachedRepository.getAllBonuses(mult);
    }

    @Override
    public BonusChoosableTypes getType() {
        return BonusChoosableTypes.DOUBLECHOOSE;
    }

}

