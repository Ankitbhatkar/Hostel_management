package com.demo.deutschebank.team2project.dto;

import lombok.Data;

@Data
public class GetOrganizationDto {
    private int id;
    private String name;
    private int hostelCount;
    private String address;
    private long contactNumber;
    private String email;
}
