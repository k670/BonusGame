package com.example.demo.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BonusActionResponse {
    private String name;
    private double value;
    private double win;
}
