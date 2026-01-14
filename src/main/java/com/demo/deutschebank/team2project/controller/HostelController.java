package com.demo.deutschebank.team2project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.deutschebank.team2project.dto.GetHostelDto;
import com.demo.deutschebank.team2project.dto.HostelDto;
import com.demo.deutschebank.team2project.service.HostelService;

import java.util.List;

@RestController
public class HostelController {

	@Autowired
	private HostelService hostelService;
	
	@GetMapping("hostel/{hostelId}")
	public ResponseEntity<?> getHostelById(@PathVariable int hostelId) {
		GetHostelDto hostel = hostelService.getHostelById(hostelId);
		return new ResponseEntity<GetHostelDto>(hostel, HttpStatus.OK);
	}

	 @PostMapping("hostel/{orgId}")
    public ResponseEntity<?> saveHostel( @PathVariable int orgId, @RequestBody HostelDto hostelDto) {
        hostelService.addHostel(orgId,hostelDto);
        return new ResponseEntity<String>("Hostel added successfully", HttpStatus.CREATED);
	}

	@GetMapping("hostels")
	public ResponseEntity<?> getAllHostels() {

		List<GetHostelDto> hostelList = hostelService.getAllHostels();
		return new ResponseEntity<List<GetHostelDto>>(hostelList, HttpStatus.OK);
	}

	@GetMapping("hostels/{organizationId}")
	public ResponseEntity<?> getHostelsByOrgId(@PathVariable Integer organizationId) {
		List<GetHostelDto> hostels = hostelService.getByOrganizationId(organizationId);
		return new ResponseEntity<>(hostels, HttpStatus.OK);
	}

    @GetMapping("filterHostel")
    public ResponseEntity<?> filterHostel(@RequestParam(required = false) String area, @RequestParam(required = false) String city) {
        return new ResponseEntity<>(hostelService.filterHostel(area,city), HttpStatus.OK);
    }
	@DeleteMapping("hostel/{hostelId}")
	public ResponseEntity<?> deleteHostel(@PathVariable int hostelId) {

		hostelService.deleteHostelById(hostelId);
		return new ResponseEntity<String>("Hostel deleted successfully", HttpStatus.OK);
	}
}
