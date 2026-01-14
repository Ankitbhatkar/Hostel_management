package com.demo.deutschebank.team2project.customexception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BuildingServiceException extends RuntimeException
{
	private String message;
	private HttpStatus httpStatus;
	
	public BuildingServiceException(String message, HttpStatus httpStatus) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
	}
	
	 @Override
	    public String getMessage() {
	        return message;
	    }
	
	
	
}
