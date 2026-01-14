package com.demo.deutschebank.team2project.dto;

import lombok.Data;

@Data
public class GetFloorDto {

    private int id;
    private int floorNo;
    private int roomCount;
    private String buildingName;
    private String hostelName;
    private String orgName;
}
