package com.demo.deutschebank.team2project.customexception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrganizationServiceException extends RuntimeException {

    private String errorMessage;
    private HttpStatus httpStatus;

    public OrganizationServiceException(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}