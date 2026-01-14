package com.demo.deutschebank.team2project.service;

public interface OtpService {
	String generateOtp(String email);

	boolean verifyOtp(String email, int otp);
}
