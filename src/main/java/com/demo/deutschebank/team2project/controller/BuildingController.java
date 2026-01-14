package com.demo.deutschebank.team2project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.deutschebank.team2project.dto.BuildingDto;
import com.demo.deutschebank.team2project.service.BuildingService;

@RestController
@RequestMapping("/building")
public class BuildingController {
	
	@Autowired
	BuildingService buildingService;
	
	@PostMapping("/addBuilding/{hostelId}")
	public ResponseEntity addBuilding(@RequestBody BuildingDto buildingDto,@PathVariable int hostelId) {
		buildingService.addBuildingByHid(buildingDto,hostelId);
		return new ResponseEntity("Building Added Succesfully", HttpStatus.CREATED);
	}
	
	@GetMapping("getBuildingByHid/{hId}")
	public ResponseEntity getBuildingsByHid(@PathVariable int hId)
	{
		return new ResponseEntity(buildingService.getBuildingsByHid(hId),HttpStatus.OK);
	}
	
	
	@GetMapping("/buildingById/{id}")
	public ResponseEntity getBuildingById(@PathVariable int id)
	{
		return new ResponseEntity(buildingService.getBuildingById(id),HttpStatus.OK);
	}

	@GetMapping("/getAllBuildings")
	public ResponseEntity getAllBuildings()
	{
		return new ResponseEntity(buildingService.getAllBuildings(),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteBuildingById/{id}")
	public ResponseEntity deleteBuildingById(@PathVariable int id)
	{
		buildingService.deleteBuildingById(id);
		return new ResponseEntity("Building has deleted Succesfully for id : "+id,HttpStatus.OK);
	}
	
	
	
}
