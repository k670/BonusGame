package com.example.demo.repository;

import com.example.demo.model.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;


public interface UserRepository extends CrudRepository<UserModel, Integer> {

    @Override
    Collection<UserModel> findAll();
}
