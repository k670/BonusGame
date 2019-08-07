package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
//@Table(name = "BonusModel")
public class BonusModel {
    @Id
    //@GeneratedValue
    //@Column(name = "Id", nullable = false)
    private final int id;

    //@Column(name = "Value")
    private final int value;

}
