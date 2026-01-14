package com.demo.deutschebank.team2project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.deutschebank.team2project.service.OtpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/otp")
public class OtpController {

	@Autowired
	private OtpService otpService;

	@PostMapping("/sendotp")
	public ResponseEntity<String> sendOtp(@RequestParam String email) {
		log.info("Send OTP API called for email: {}", email);
		otpService.generateOtp(email);
		return new ResponseEntity<>("email send..", HttpStatus.OK);
	}

	@PostMapping("/verify")
	public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam int otp) {
		log.info("Verify OTP API called for email: {}", email);
		boolean isValid = otpService.verifyOtp(email, otp);
		return new ResponseEntity<>("OTP verified successfully", HttpStatus.OK);
	}
}
