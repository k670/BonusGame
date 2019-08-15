package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "coins")
    private double coins;


}
