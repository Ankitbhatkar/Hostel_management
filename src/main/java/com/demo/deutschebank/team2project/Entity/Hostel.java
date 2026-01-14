package com.demo.deutschebank.team2project.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Hostel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private int capacity;
	private String type;
	private long contactNumber;
//    @Lob
//    @Column(columnDefinition = "LONGBLOB")
    private String image;

    private int buildingCount=0;
    private String website;
    private double latitude;
    private double longitude;

	@OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Building> buildings;

	@ManyToOne
	@JoinColumn(name = "orgnization_id")
	@JsonIgnore
	private Orgnization orgnization;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
