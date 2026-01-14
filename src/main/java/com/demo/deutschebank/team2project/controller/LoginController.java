package com.demo.deutschebank.team2project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.deutschebank.team2project.Entity.User;
import com.demo.deutschebank.team2project.dto.LoginDto;
import com.demo.deutschebank.team2project.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginDto loginDto) {

		userService.loginWithUsernameOrEmail(loginDto.getUsername(), loginDto.getPassword());

		return new ResponseEntity("Login Successfully", HttpStatus.OK);
	}
}
