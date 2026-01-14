package com.demo.deutschebank.team2project.dto;

import lombok.Data;

@Data
public class GetHostelDto {
   private int id;
   private String name;
   private String email;
   private String area;
   private String city;
   private int zipCode;
   private String state;
   private String country;
   private double latitude;
   private double longitude;
   private int capacity;
   private String type;
   private long contactNumber;
   private String image;
   private int buildingCount;
   private String website;
}