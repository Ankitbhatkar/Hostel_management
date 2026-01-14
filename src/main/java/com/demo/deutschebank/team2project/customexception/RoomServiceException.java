package com.demo.deutschebank.team2project.customexception;

import org.springframework.http.HttpStatus;

public class RoomServiceException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public RoomServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}