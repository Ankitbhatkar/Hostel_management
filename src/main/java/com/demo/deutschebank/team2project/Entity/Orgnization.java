package com.demo.deutschebank.team2project.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Orgnization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int hostelCount=0;
	private String address;
	private long comtactNumber;
	private String email;

	@OneToMany(mappedBy = "orgnization", cascade = CascadeType.ALL)
	private List<Hostel> hostels;

}
