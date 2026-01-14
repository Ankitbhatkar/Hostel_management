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

import com.demo.deutschebank.team2project.dto.BedDto;
import com.demo.deutschebank.team2project.dto.GetBedDto;
import com.demo.deutschebank.team2project.service.BedService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/bed")
public class BedController {

	@Autowired
	BedService bedService;

	@PostMapping("/addBedByRoomId/{roomId}")
	public ResponseEntity<?> addBedByRoomId(@PathVariable int roomId, @RequestBody BedDto bedDto) {
		bedService.addBedByRoomId(roomId, bedDto);
		return new ResponseEntity("Bed added successfully", HttpStatus.CREATED);
	}

	@GetMapping("/getBedById/{bedId}")
	public ResponseEntity<?> getBedById(@PathVariable int bedId) {
		GetBedDto bed = bedService.getBedById(bedId);
		return new ResponseEntity(bed, HttpStatus.OK);
	}

	@GetMapping("/getAllBeds")
	public ResponseEntity<?> getAllBeds() {
		return new ResponseEntity(bedService.getAllBeds(), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{bedId}")
	public ResponseEntity<?> deleteBed(@PathVariable int bedId) {
		bedService.deleteBedById(bedId);
		return new ResponseEntity("Bed deleted successfully", HttpStatus.OK);
	}

	@GetMapping("/getBedbyRoomId/{roomId}")
	public ResponseEntity<?> getBedsByRoomId(@PathVariable int roomId) {
		return new ResponseEntity(bedService.getBedsByRoomId(roomId), HttpStatus.OK);
	}
	
	@GetMapping("/getVaccantBeds/{status}/{sharing}")
	public ResponseEntity getVaccantBedBySharing(@PathVariable String status, @PathVariable int sharing)
	{
		return new ResponseEntity<>(bedService.getAvailableBedBySharing(status, sharing),HttpStatus.OK);
	}

}
