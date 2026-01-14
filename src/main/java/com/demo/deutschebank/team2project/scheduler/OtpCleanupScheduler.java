package com.demo.deutschebank.team2project.scheduler;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.demo.deutschebank.team2project.Repository.OtpRepo;

import jakarta.transaction.Transactional;

@Component
public class OtpCleanupScheduler {

    @Autowired
    private OtpRepo otpRepo;

    @Transactional
    @Scheduled(fixedRate = 400000)
    public void deleteExpiredOtps() {
        otpRepo.deleteByExpiryTimeBefore(LocalDateTime.now());
    }
}
