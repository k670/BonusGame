package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BonusModel {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonIgnore
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private double value;

    @Column(name = "chance")
    @Min(0)
    @JsonIgnore
    private double chance;


}
