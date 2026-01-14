package com.demo.deutschebank.team2project.dto;

import lombok.Data;

@Data
public class GetBuildingDto {

	private int id;
	private String name;
	private int floorCount;
	private String wardenName;
	private String wardenEmail;
	private long wardenContactNo;
	
	private String hostelName;
	private String orgnizationName;
	
}
