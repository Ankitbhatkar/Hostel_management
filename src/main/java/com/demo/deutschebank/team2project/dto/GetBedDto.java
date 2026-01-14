package com.demo.deutschebank.team2project.dto;

import lombok.Data;

@Data
public class GetBedDto {

	private int id;
	private String bedNo;
	private String status;
	private int price;
	private int deposit;
	
	private int roomId;
	private String floorNo;
	private String buildingName;
	private String hostelName;
	private String orgName;

}
