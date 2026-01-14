
package com.demo.deutschebank.team2project.dto;

import lombok.Data;

@Data
public class GetRoomDto {
	private int id;
	private int roomNo;
	private int sharing;
	private String type;
	private String floorName;
	private String buildingName;
	private String hostelName;
	private String orgName;

}
