package com.demo.deutschebank.team2project.customexception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserServiceException extends RuntimeException {
	private String eMassage;
	private HttpStatus httpStatus;

	public UserServiceException(String eMassage, HttpStatus httpStatus) {
		this.eMassage = eMassage;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return eMassage;
	}


}