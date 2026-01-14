package com.demo.deutschebank.team2project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.deutschebank.team2project.Entity.Building;

public interface BuildingRepo extends JpaRepository<Building, Integer> {

	@Query("SELECT b FROM Building b WHERE b.hostel.id = :hostelId")
	public List<Building> getBuildingsByHid(@Param("hostelId") int hostelId);

	 List<Building> findByHostel_Id(int hostelId);
}
