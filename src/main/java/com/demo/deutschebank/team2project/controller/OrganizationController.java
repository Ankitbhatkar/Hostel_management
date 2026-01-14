package com.demo.deutschebank.team2project.controller;

import com.demo.deutschebank.team2project.dto.AddOrgnizationDTO;
import com.demo.deutschebank.team2project.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class OrganizationController {
    @Autowired
    OrganizationService organizationService;

    @PostMapping("organizations")
    public ResponseEntity<?> addOrganization(@RequestBody AddOrgnizationDTO addOrgnizationDTO) {
        organizationService.addOrganization(addOrgnizationDTO);
        return new ResponseEntity<>("Organization added successfully", HttpStatus.CREATED);
    }

    @GetMapping("organizations/{organizationId}")
    public ResponseEntity<?> getOrganizationById(@PathVariable int organizationId) {
        return new ResponseEntity<>(organizationService.getOrganizationById(organizationId), HttpStatus.OK);
    }

    @GetMapping("organizations")
    public ResponseEntity<?> getAllOrganizations() {
        return new ResponseEntity<>(organizationService.getAllOrganizations(), HttpStatus.OK);
    }

    @DeleteMapping("organizations/{organizationId}")
    public ResponseEntity<?> deleteOrganization(@PathVariable int organizationId) {
        organizationService.deleteOrganization(organizationId);
        return new ResponseEntity<>("Organization deleted successfully", HttpStatus.OK);
    }
}