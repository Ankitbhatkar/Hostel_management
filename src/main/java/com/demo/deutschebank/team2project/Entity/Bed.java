package com.demo.deutschebank.team2project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Bed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String bedNo;
	private String status;
	private int price;
	private int deposit;
	

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "room_id")
	private Room room;

}
