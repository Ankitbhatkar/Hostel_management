package com.demo.deutschebank.team2project.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.deutschebank.team2project.Entity.Otp;
import com.demo.deutschebank.team2project.Entity.User;
import com.demo.deutschebank.team2project.Repository.OtpRepo;
import com.demo.deutschebank.team2project.Repository.UserRepo;
import com.demo.deutschebank.team2project.customexception.OtpServiceException;
import com.demo.deutschebank.team2project.service.OtpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OtpServiceImpl implements OtpService {
	@Autowired
	OtpRepo otpRepo;
	@Autowired
	private UserRepo userRepo;

	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Override
	public String generateOtp(String email) {
		log.info("Generate OTP request received for email: {}", email);

		if (email == null || email.isEmpty()) {
			log.error("Email is null or empty");
			throw new OtpServiceException("Email must not be null or empty", HttpStatus.BAD_REQUEST);
		}

		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			log.error("Invalid email format received: {}", email);
			throw new OtpServiceException("Invalid email format", HttpStatus.BAD_REQUEST);
		}

		int otp = createOTP(email);

		log.info("OTP generated successfully for email: {}", email);

		emailServiceImpl.sendEMail(email, otp);
		log.info("OTP email sent successfully to: {}", email);

		return "OTP sent successfully";
	}

	private int createOTP(String email) {
		log.debug("Creating OTP for email: {}", email);

		User user = userRepo.findByEmail(email);
		if (user == null) {
			log.error("User not found while creating OTP for email: {}", email);
			throw new OtpServiceException("User not found", HttpStatus.NOT_FOUND);
		}

		int generatedOtp = new Random().nextInt(900000) + 100000;

		Otp otp = new Otp();
		otp.setEmail(email);
		otp.setOtp(generatedOtp);
		otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
		otp.setVerify(false);
		otp.setUser(user);

		otpRepo.save(otp);
		log.debug("OTP saved with user_id={} for email={}", user.getId(), email);

		return generatedOtp;
	}

	@Override
	public boolean verifyOtp(String email, int otp) {

		log.info("Verify OTP request for email: {}", email);

		if (email == null || email.isEmpty()) {
			log.error("Email is empty during OTP verification");

			throw new OtpServiceException("Email must not be empty", HttpStatus.BAD_REQUEST);
		}
		Optional<Otp> otpps = otpRepo.findByEmailAndOtp(email, otp);
		if (!otpps.isPresent()) {
			log.warn("OTP not found for email: {}", email);

			throw new OtpServiceException("OTP not found for given email", HttpStatus.NOT_FOUND);
		}

		Otp storedOtp = otpps.get();
		if (storedOtp.isVerify()) {
			log.warn("OTP already verified for email: {}", email);

			throw new OtpServiceException("OTP already verified", HttpStatus.BAD_REQUEST);
		}
		if (storedOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
			log.warn("OTP expired for email: {}", email);

			throw new OtpServiceException("OTP has expired", HttpStatus.BAD_REQUEST);
		}
		storedOtp.setVerify(true);
		otpRepo.save(storedOtp);
		log.info("OTP verified successfully for email: {}", email);

		return true;

	}

}
