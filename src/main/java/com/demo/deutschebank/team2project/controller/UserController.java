package com.demo.deutschebank.team2project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.deutschebank.team2project.Entity.User;
import com.demo.deutschebank.team2project.dto.UserSignupDto;
import com.demo.deutschebank.team2project.dto.UserUpdateDto;
import com.demo.deutschebank.team2project.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity signUp(@RequestBody UserSignupDto signUpDto) {
		userService.signUp(signUpDto);
		return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
	}

	@GetMapping("/byUsername")
	public ResponseEntity<User> getByUsername(@RequestParam String username) {
		User user = userService.getByUsername(username);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("/byEmail")
	public ResponseEntity<User> getByEmail(@RequestParam String email) {
		User user = userService.getByEmail(email);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("/byUsernameOrEmail")
	public ResponseEntity<List<User>> getByUsernameOrEmail(@RequestParam String value) {

		List<User> users = userService.getByUsernameOrEmail(value);

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PatchMapping("/update/{id}")
	public ResponseEntity<User> updateUserPartial(@PathVariable int id, @RequestBody UserUpdateDto dto) {

		User updatedUser = userService.updateUser(id, dto);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

}
