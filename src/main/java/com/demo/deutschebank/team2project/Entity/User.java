package com.demo.deutschebank.team2project.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "users")
public class User {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
		private String name; 
		private String address; 
		private String email; 
		private long contactNo; 
		private String password; 

		private String gender; 
		private String dob; 
		private String city; 
		private String state; 
		private String country; 

		private int pincode; 
		private String userType; 
		private String username; 
		private String status; 
		private LocalDate createdDate; 

		private LocalDate updatedDate; 
	//	@Lob
	//	@Column(columnDefinition = "LONGBLOB") //database to store this field as a Large Binary Object.and hd image 
	//	private byte[] profileImage; 
		
		private long altContactNo; 
		private long aadhaarNo; 
		private String maritalStatus;

		@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
		private List<Otp> otps;

	}