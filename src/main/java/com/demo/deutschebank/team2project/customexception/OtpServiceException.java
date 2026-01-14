package com.demo.deutschebank.team2project.customexception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
@Getter
public class OtpServiceException extends RuntimeException {
	private String eMassage;
	private HttpStatus httpStatus;

	public OtpServiceException(String eMassage, HttpStatus httpStatus) {
		this.eMassage = eMassage;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return eMassage;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
