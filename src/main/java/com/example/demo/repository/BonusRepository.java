package com.example.demo.repository;

import com.example.demo.model.BonusModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface BonusRepository extends CrudRepository<BonusModel, Integer> {

    @Override
    Collection<BonusModel> findAll();


}

