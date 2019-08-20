package com.example.demo.service;

import com.example.demo.model.BonusModel;
import com.example.demo.service.choosealgorithm.BonusChoosable;
import com.example.demo.service.choosealgorithm.BonusChoosableTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.service.choosealgorithm.BonusChoosableTypes.DOUBLECHOOSE;
import static com.example.demo.service.choosealgorithm.BonusChoosableTypes.EXCEPTIONCHOOSE;
import static com.example.demo.service.choosealgorithm.BonusChoosableTypes.SIMPLECHOOSE;

@Slf4j
@Service("bonusService")
public class BonusService {

    private Map<BonusChoosableTypes, BonusChoosable> bonusChoosableMap = new HashMap<>();
    private final Collection<BonusChoosable> choosables;

    @Autowired
    public BonusService(Collection<BonusChoosable> choosables) {

        this.choosables = choosables;
    }

    public Collection<BonusModel> getAllBonuses(int userId) {
        BonusChoosableTypes type = getBonusChoosableTypes(userId);
        return bonusChoosableMap.get(type).getAllBonus(userId);
    }

    //win = bonusModel.getChance()
    public BonusModel chooseBonuse(double bet, int userId) {
        if (bet <= 0) {
            throw new RuntimeException("Wrong bet");
        }
        BonusChoosableTypes type = getBonusChoosableTypes(userId);
        return bonusChoosableMap.get(type).chooseBonuse(userId, bet);
    }

    private BonusChoosableTypes getBonusChoosableTypes(int userId) {
        BonusChoosableTypes type;
        if (userId <= 0) {
            type = EXCEPTIONCHOOSE;
        } else {
            type = userId % 2 == 0 ? DOUBLECHOOSE : SIMPLECHOOSE;
        }
        return type;
    }


    @PostConstruct
    public void readBonusRepo() {
        choosables.forEach(c -> bonusChoosableMap.put(c.getType(), c));
    }

}
