  package com.demo.deutschebank.team2project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.demo.deutschebank.team2project.dto.FloorDto;
import com.demo.deutschebank.team2project.service.FloorService;

@RestController
@RequestMapping("/floor")
public class FloorController {

	@Autowired
	FloorService floorService;

	@PostMapping("/add/{buildingId}")
	ResponseEntity<String> saveFloor(@PathVariable int buildingId, @RequestBody FloorDto floorDto) {
		floorService.addFloor(buildingId, floorDto);
		return new ResponseEntity<>("Floor added successfully", HttpStatus.CREATED);
	}

    @GetMapping("/getByFloorId/{floorId}")
    ResponseEntity<?> getFloorById(@PathVariable int floorId) {
        return new ResponseEntity<>(floorService.getFloorById(floorId), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    ResponseEntity<?> getAllFloors() {
        return new ResponseEntity<>(floorService.getAllFloors(), HttpStatus.OK);
    }

    @GetMapping("/getByBuildingId/{buildingId}")
    ResponseEntity<?> getFloorByBuildingId(@PathVariable int buildingId) {
        return new ResponseEntity<>(floorService.getFloorByBuildingId(buildingId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{floorId}")
    ResponseEntity<?> deleteFloorById(@PathVariable int floorId) {
        floorService.deleteFloorById(floorId);
        return new ResponseEntity<>("Floor deleted successfully", HttpStatus.OK);
    }


}