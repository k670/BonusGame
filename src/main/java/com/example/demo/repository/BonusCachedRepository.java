package com.example.demo.repository;

import com.example.demo.model.BonusModel;
import com.example.demo.repository.dtoRepository.SearchMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.NavigableMap;
import java.util.TreeMap;

@Component
@EnableCaching
public class BonusCachedRepository {

    private BonusRepository bonusRepository;

    @Autowired
    public BonusCachedRepository(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    @Cacheable("allBonus")
    public Collection<BonusModel> getAllBonuses(int mult) {
        Collection<BonusModel> collection = bonusRepository.findAll();

        for (BonusModel bm : collection) {
            bm.setValue(mult * bm.getValue());
        }
        return collection;
    }

    @Cacheable("bonusMap")
    public SearchMap getBonusMap(int mult) {
        NavigableMap<Double, BonusModel> bonusModelMap = new TreeMap<>();
        BonusModel[] objectBonusModel = getAllBonuses(mult).toArray(new BonusModel[0]);
        if (objectBonusModel.length == 0) {
            return null;
        }
        bonusModelMap.put(objectBonusModel[0].getChance(), objectBonusModel[0]);
        for (int i = 1; i < objectBonusModel.length; i++) {
            bonusModelMap.put(objectBonusModel[i].getChance() + bonusModelMap.lastEntry().getKey(), objectBonusModel[i]);
        }

        return SearchMap.builder().map(bonusModelMap).build();
    }

    @CacheEvict(value = "allBonus, bonusMap", allEntries = true)
    public BonusModel update(BonusModel bonusModel) {
        return bonusRepository.save(bonusModel);
    }

}
