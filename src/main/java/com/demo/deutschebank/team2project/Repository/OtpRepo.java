package com.demo.deutschebank.team2project.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.deutschebank.team2project.Entity.Otp;

public interface OtpRepo extends JpaRepository<Otp, Integer> {
	Optional<Otp> findByEmailAndOtp(String email, int otp);
	Otp findByEmail(String email);

	void deleteByExpiryTimeBefore(LocalDateTime now);
}
