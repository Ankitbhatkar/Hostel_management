package com.demo.deutschebank.team2project.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Building {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private int floorCount=0;
	private String wardenName;
	private String wardenEmail;
	private long wardenContactNo;

	@OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
	private List<Floor> floors;

	@ManyToOne
	@JoinColumn(name = "hostel_id")
	private Hostel hostel;

}
