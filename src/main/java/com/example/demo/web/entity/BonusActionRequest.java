package com.example.demo.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonusActionRequest {
    @Min(0)
    double bet;
    @Min(1)
    int userId;
}
