package com.example.demo.repository;

import java.util.ArrayList;

public class BonusRepository {
    private ArrayList<Integer> bonuses;

    public BonusRepository(){
        bonuses = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            bonuses.add(i);
        }
    }

    public ArrayList<Integer> getBonuses(){
        return bonuses;
    }

    public int getOneBonuse(int index){
        return bonuses.get(index);
    }
}
