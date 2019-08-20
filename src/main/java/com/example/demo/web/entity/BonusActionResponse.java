package com.example.demo.web.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BonusActionResponse {
    private String name;
    private double value;
    private double win;
}
