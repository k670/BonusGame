package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;


@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BonusModel {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "value")
    private int value;

    @Column(name = "chance")
    @Min(0)
    private int chance;


}
