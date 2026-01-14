package com.demo.deutschebank.team2project.dto;



import lombok.Data;

@Data
public class HostelDto {

    private String name;
    private String email;
    private int capacity;
    private String type;
    private long contactNumber;
    private String image;
    private String website;
    private double latitude;
    private double longitude;
    private AddAddressDto address;

}
