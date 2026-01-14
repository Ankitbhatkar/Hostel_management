package com.demo.deutschebank.team2project.customexception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Getter;

@Getter
public class BedServiceException extends RuntimeException {

	 private String errorMessage;
	    private HttpStatus httpStatus;

	    public BedServiceException(String errorMessage, HttpStatus httpStatus) {
	        this.errorMessage = errorMessage;
	        this.httpStatus = httpStatus;
	    }

	    @Override
	    public String getMessage() {
	        return super.getMessage();
	    }
	
}
