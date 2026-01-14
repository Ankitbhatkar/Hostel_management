package com.demo.deutschebank.team2project.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String area;
    private String city;
    private int zipCode;
    private String state;
    private String country = "India";
}
