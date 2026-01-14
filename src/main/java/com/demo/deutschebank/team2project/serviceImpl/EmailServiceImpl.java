package com.demo.deutschebank.team2project.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.demo.deutschebank.team2project.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public void sendEMail(String email, int otp) {

		log.info("Preparing to send OTP email to: {}", email);

		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setFrom("rtayde474@gmail.com");
			message.setSubject("Your OTP Code");
			message.setText("Your OTP is: " + otp);

			javaMailSender.send(message);

			log.info("OTP email successfully sent to: {}", email);

		} catch (Exception e) {
			log.error("Failed to send OTP email to " + email);
		}
	}

}
