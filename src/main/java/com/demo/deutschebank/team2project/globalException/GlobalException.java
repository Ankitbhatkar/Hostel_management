package com.demo.deutschebank.team2project.globalException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.deutschebank.team2project.customexception.BedServiceException;
import com.demo.deutschebank.team2project.customexception.BuildingServiceException;
import com.demo.deutschebank.team2project.customexception.FloorServiceException;
import com.demo.deutschebank.team2project.customexception.HostelServiceException;
import com.demo.deutschebank.team2project.customexception.OrganizationServiceException;
import com.demo.deutschebank.team2project.customexception.OtpServiceException;
import com.demo.deutschebank.team2project.customexception.RoomServiceException;
import com.demo.deutschebank.team2project.customexception.UserServiceException;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(exception = UserServiceException.class)
	public ResponseEntity<String> handleUserserviceException(UserServiceException userServiceException) {
		return new ResponseEntity<>(userServiceException.getMessage(), userServiceException.getHttpStatus());
	}

	@ExceptionHandler
	public ResponseEntity<?> globalOrganizationExceptionHandler(OrganizationServiceException exception) {
		return new ResponseEntity<>(exception.getErrorMessage(), exception.getHttpStatus());
	}

	@ExceptionHandler(exception = HostelServiceException.class)
	public ResponseEntity<String> handleHostelServiceException(HostelServiceException hostelServiceException) {
		return new ResponseEntity<>(hostelServiceException.getMessage(), hostelServiceException.getHttpStatus());
	}

	@ExceptionHandler(exception = BuildingServiceException.class)
	public ResponseEntity<String> handleBuildingServiceException(BuildingServiceException buildingServiceException) {
		return new ResponseEntity<>(buildingServiceException.getMessage(), buildingServiceException.getHttpStatus());
	}

	@ExceptionHandler(exception = FloorServiceException.class)
	public ResponseEntity<String> handleFloorServiceException(FloorServiceException floorServiceException) {
		return new ResponseEntity<>(floorServiceException.getMessage(), floorServiceException.getHttpStatus());
	}

	@ExceptionHandler(exception = RoomServiceException.class)
	public ResponseEntity<String> handleRoomServiceException(RoomServiceException roomServiceException) {
		return new ResponseEntity<>(roomServiceException.getMessage(), roomServiceException.getHttpStatus());
	}

	@ExceptionHandler(exception = BedServiceException.class)
	public ResponseEntity<String> handleBedServiceException(BedServiceException bedServiceException) {
		return new ResponseEntity<>(bedServiceException.getMessage(), bedServiceException.getHttpStatus());
	}

	@ExceptionHandler(exception = OtpServiceException.class)
	public ResponseEntity<String> handleOtpServiceException(OtpServiceException otpServiceException) {
		return new ResponseEntity<>(otpServiceException.getMessage(), otpServiceException.getHttpStatus());
	}

	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<String> handleException(Exception exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
