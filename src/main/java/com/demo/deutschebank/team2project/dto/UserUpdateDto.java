package com.demo.deutschebank.team2project.dto;

import lombok.Data;

@Data
public class UserUpdateDto {

	private String address;
	private String city;
	private String state;
	private String country;
	private Integer pincode;
	private String gender;
	private String dob;
	private Long altContactNo;
	private Long aadhaarNo;
	private String maritalStatus;
}
