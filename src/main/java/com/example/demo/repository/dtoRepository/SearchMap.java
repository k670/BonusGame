package com.example.demo.repository.dtoRepository;

import com.example.demo.model.BonusModel;
import lombok.Builder;
import lombok.Value;

import java.util.NavigableMap;

@Value
@Builder
public class SearchMap {
    NavigableMap<Double, BonusModel> map;
}
