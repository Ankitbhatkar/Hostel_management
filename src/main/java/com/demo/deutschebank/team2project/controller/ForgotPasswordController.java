package com.demo.deutschebank.team2project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.deutschebank.team2project.service.ForgotPasswordService;


@RestController
@RequestMapping("/auth")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotService;

    @PostMapping("/sendOtp")
    public String sendOtp(@RequestParam String email) {
        return forgotService.sendOtp(email);
    }

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp) {

        boolean isValid = forgotService.verifyOtp(email, otp);

        return isValid ? "OTP verified!" : "Invalid or expired OTP!";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword) {

        return forgotService.resetPassword(email, newPassword);
    }
}
