package com.example.demo.service.dtoApi;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BonusWinModel {
    String name;
    double value;
    double win;
}
