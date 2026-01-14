package com.demo.deutschebank.team2project.customexception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HostelServiceException extends RuntimeException {

    private String errorMessage;
    private HttpStatus httpStatus;

    public HostelServiceException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
