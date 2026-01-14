package com.demo.deutschebank.team2project.service;

public interface ForgotPasswordService {

   
    String sendOtp(String email);

    boolean verifyOtp(String email, String otp);

    String resetPassword(String email, String newPassword);
}