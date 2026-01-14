

package com.demo.deutschebank.team2project.customexception;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
public class FloorServiceException extends RuntimeException {
	
	 private String errorMessage;
	    private HttpStatus httpStatus;
	    
	    public  FloorServiceException (String errorMessage, HttpStatus httpStatus) {
	        this.errorMessage = errorMessage;
	        this.httpStatus = httpStatus;
	    }

	    @Override
	    public String getMessage() {
	        return errorMessage;
	    }
}
