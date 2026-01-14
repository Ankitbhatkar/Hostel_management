package com.demo.deutschebank.team2project.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.deutschebank.team2project.Entity.User;
import com.demo.deutschebank.team2project.Repository.UserRepo;
import com.demo.deutschebank.team2project.customexception.UserServiceException;
import com.demo.deutschebank.team2project.dto.UserSignupDto;
import com.demo.deutschebank.team2project.dto.UserUpdateDto;
import com.demo.deutschebank.team2project.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;

	public void signUp(UserSignupDto signupDto) {

		User byUsername = userRepo.findByUsername(signupDto.getUsername());

		if (byUsername != null) {
			throw new UserServiceException("Username already exists", HttpStatus.CONFLICT);
		}
		User existingEmail = userRepo.findByEmail(signupDto.getEmail());
		if (existingEmail != null) {
			throw new UserServiceException("Email already exists", HttpStatus.CONFLICT);
		}

		User users = new User();
		users.setName(signupDto.getName());
		users.setEmail(signupDto.getEmail());
		users.setPassword(signupDto.getPassword());
		users.setUsername(signupDto.getUsername());
		users.setContactNo(signupDto.getContactNumber());
		users.setUserType("USER");
		users.setStatus("ACTIVE");
		users.setCreatedDate(LocalDate.now());

		try {
			userRepo.save(users);
		} catch (Exception exception) {
			throw new UserServiceException("Error while saving user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@Override
//	public User login(String username, String password) {
//
//		User user = userRepo.findByUsernameAndPassword(username, password);
//
//		if (user == null) {
//			throw new UserServiceException("Invalid credentials", HttpStatus.UNAUTHORIZED);
//		}
//
//		return user;
//	}

	@Override
	public User getByUsername(String username) {
		User user = userRepo.findByUsername(username);

		if (user == null) {
			throw new UserServiceException("User not found", HttpStatus.NOT_FOUND);
		}

		return user;
	}

	@Override
	public User getByEmail(String email) {
		User user = userRepo.findByEmail(email);

		if (user == null) {
			throw new UserServiceException("User not found", HttpStatus.NOT_FOUND);
		}

		return user;
	}

	@Override
	public User loginWithUsernameOrEmail(String username, String password) {
		User user = userRepo.loginByUsernameOrEmail(username, password);

		if (user == null) {
			throw new UserServiceException("Invalid username/email or password", HttpStatus.UNAUTHORIZED);
		}

		return user;
	}

	@Override
	public List<User> getByUsernameOrEmail(String value) {
		List<User> users = userRepo.findByUsernameOrEmail(value);

		if (users.isEmpty()) {
			throw new UserServiceException("User Not Found", HttpStatus.NOT_FOUND);
		}

		return users;

	}

	@Override
	public User updateUser(int id, UserUpdateDto dto) {
		User user = userRepo.findById(id).orElse(null);

		if (user == null) {
			throw new UserServiceException("User Not Found", HttpStatus.NOT_FOUND);
		}

		if (dto.getAddress() != null && !dto.getAddress().equals(user.getAddress())) {
			user.setAddress(dto.getAddress());
		}
		if (dto.getCity() != null && !dto.getCity().equals(user.getCity())) {
			user.setCity(dto.getCity());
		}
		if (dto.getState() != null && !dto.getState().equals(user.getState())) {
			user.setState(dto.getState());
		}
		if (dto.getCountry() != null && !dto.getCountry().equals(user.getCountry())) {
			user.setCountry(dto.getCountry());
		}
		if (dto.getPincode() != null && dto.getPincode() != user.getPincode()) {
			user.setPincode(dto.getPincode());
		}
		if (dto.getGender() != null && !dto.getGender().equals(user.getGender())) {
			user.setGender(dto.getGender());
		}
		if (dto.getDob() != null && !dto.getDob().equals(user.getDob())) {
			user.setDob(dto.getDob());
		}
		if (dto.getAltContactNo() != null && dto.getAltContactNo() != user.getAltContactNo()) {
			user.setAltContactNo(dto.getAltContactNo());
		}
		if (dto.getAadhaarNo() != null && dto.getAadhaarNo() != user.getAadhaarNo()) {
			user.setAadhaarNo(dto.getAadhaarNo());
		}
		if (dto.getMaritalStatus() != null && !dto.getMaritalStatus().equals(user.getMaritalStatus())) {
			user.setMaritalStatus(dto.getMaritalStatus());
		}

		user.setUpdatedDate(LocalDate.now());

		return userRepo.save(user);
	}

	@Override
	public List<User> getAllUsers() {

		return userRepo.findAll();
	}
}
