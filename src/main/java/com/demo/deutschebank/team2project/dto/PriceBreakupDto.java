package com.demo.deutschebank.team2project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceBreakupDto {

    private int bedId;
    private String bedNo;
    private int roomNo;
    private int floorNo;
    private String buildingName;
    private String hostelName;

    private int price;
    private int deposit;
    private double finalAmount;
    private int durationInMonth;
}
