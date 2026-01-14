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
public class Floor {
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private int floorNo;
	    private int roomCount;

	    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
	    private List<Room> rooms;

	    @ManyToOne
	    @JoinColumn(name = "building_id")
	    private Building building;

}
