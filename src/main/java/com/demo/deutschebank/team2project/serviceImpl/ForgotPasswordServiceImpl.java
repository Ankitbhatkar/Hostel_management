package com.demo.deutschebank.team2project.serviceImpl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.demo.deutschebank.team2project.Entity.Otp;
import com.demo.deutschebank.team2project.Entity.User;
import com.demo.deutschebank.team2project.Repository.OtpRepo;
import com.demo.deutschebank.team2project.Repository.UserRepo;
import com.demo.deutschebank.team2project.service.ForgotPasswordService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    private OtpRepo otpRepo;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JavaMailSender mailSender;

    
    private int generateOtp() {
        int otp = new Random().nextInt(900000) + 100000;
        log.info("Generated OTP: {}", otp);
        return otp;
    }

   
    @Transactional
    public String sendOtp(String email) {

        log.info("Send OTP request received for email: {}", email);

        if (!userRepository.existsByEmail(email)) {
            log.info("Email not found in database: {}", email);
            return "Email not found!";
        }

        int otp = generateOtp();

        Otp entity = otpRepo.findByEmail(email);

        if (entity == null) {
            log.info("No existing OTP record found. Creating new OTP entry for {}", email);
            entity = new Otp();
            entity.setEmail(email);
        } else {
            log.info("Existing OTP record found for {}", email);
        }

        entity.setOtp(otp);
        entity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        entity.setVerify(false);

        otpRepo.save(entity);
        log.info("OTP saved successfully for email: {}", email);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset OTP");
            message.setText(
                    "Your OTP is: " + otp +
                    "\nValid for 5 minutes."
            );

            mailSender.send(message);
            log.info("OTP email sent successfully to {}", email);

        } catch (Exception e) {
            log.info("Failed to send OTP email to {}", email, e);
            throw e;
        }

        return "OTP sent successfully!";
    }

    @Transactional
    public boolean verifyOtp(String email, String otp) {

        log.info("Verifying OTP for email: {}", email);

        Otp stored = otpRepo.findByEmail(email);

        if (stored == null) {
            log.info("No OTP record found for email: {}", email);
            return false;
        }

        if (stored.getExpiryTime().isBefore(LocalDateTime.now())) {
            log.info("OTP expired for email: {}", email);
            stored.setVerify(false);
            otpRepo.save(stored);
            return false;
        }

        if (stored.getOtp() == Integer.parseInt(otp)) {
            log.info("OTP verification successful for email: {}", email);
            stored.setVerify(true);
            otpRepo.save(stored);
            return true;
        }

        log.info("Invalid OTP entered for email: {}", email);
        stored.setVerify(false);
        otpRepo.save(stored);
        return false;
    }

 
    @Transactional
    public String resetPassword(String email, String newPassword) {

        log.info("Reset password request received for email: {}", email);

        Otp otp = otpRepo.findByEmail(email);

        if (otp == null || !otp.isVerify()) {
            log.info("Password reset attempted without OTP verification for {}", email);
            return "OTP not verified!";
        }

        User user = userRepository.findByEmail(email);

        if (user == null) {
            log.info("User not found while resetting password for {}", email);
            return "User not found!";
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        log.info("Password reset successfully for email: {}", email);

        return "Password reset successfully!";
    }
}
