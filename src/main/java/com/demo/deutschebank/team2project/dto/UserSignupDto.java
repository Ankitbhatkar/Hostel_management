package com.demo.deutschebank.team2project.dto;

import lombok.Data;

@Data
public class UserSignupDto {
	private String name;
	private String email;
	private String password;
	private String username;
	private long contactNumber;

}
