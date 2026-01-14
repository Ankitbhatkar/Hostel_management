package com.demo.deutschebank.team2project.service;

import java.util.List;

import com.demo.deutschebank.team2project.Entity.User;
import com.demo.deutschebank.team2project.dto.UserSignupDto;
import com.demo.deutschebank.team2project.dto.UserUpdateDto;

public interface UserService {

	void signUp(UserSignupDto signupDto);

	List<User> getAllUsers();

	User getByUsername(String username);

	User getByEmail(String email);

	User loginWithUsernameOrEmail(String username, String password);

	public List<User> getByUsernameOrEmail(String value);

	public User updateUser(int id, UserUpdateDto dto);

}
