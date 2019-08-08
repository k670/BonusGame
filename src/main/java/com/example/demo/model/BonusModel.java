package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BonusModel {

    @Id
    @GeneratedValue
    @Column
    private  int id;

    @Column
    private  int value;

    public String toString(){
        return "{\"id\":"+id+",\"value\":"+value+"}";
    }

}
