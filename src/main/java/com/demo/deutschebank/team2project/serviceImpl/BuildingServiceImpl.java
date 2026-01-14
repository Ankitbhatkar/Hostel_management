package com.demo.deutschebank.team2project.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.deutschebank.team2project.Entity.Building;
import com.demo.deutschebank.team2project.Entity.Hostel;
import com.demo.deutschebank.team2project.Repository.BuildingRepo;
import com.demo.deutschebank.team2project.Repository.FloorRepo;
import com.demo.deutschebank.team2project.Repository.HostelRepo;
import com.demo.deutschebank.team2project.customexception.BuildingServiceException;
import com.demo.deutschebank.team2project.dto.BuildingDto;
import com.demo.deutschebank.team2project.dto.GetBuildingDto;
import com.demo.deutschebank.team2project.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	BuildingRepo buildingRepo;
	@Autowired
	HostelRepo hostelRepo;
	@Autowired
	FloorRepo floorRepo;

	private static final Logger logger = LoggerFactory.getLogger(BuildingServiceImpl.class);

	@Override
	public void addBuildingByHid(BuildingDto buildingDto, int hostelId) {

		logger.info("addBuilding method execution has started succesfully for hostel id : " + hostelId);
		Optional<Hostel> hostel = hostelRepo.findById(hostelId);
		logger.info("hostel is founding in database for hostel id : " + hostelId);
		if (hostel.isEmpty()) {
			logger.info("hostel is not found for hostel id : " + hostelId);
			throw new BuildingServiceException("Hostel is not found for this hostel id : " + hostelId,
					HttpStatus.NOT_FOUND);
		}

		logger.info("Hostel is found succesfully for id : " + hostelId);
		Hostel hostel1 = hostel.get();

		Building building = new Building();
		building.setName(buildingDto.getName());
		building.setWardenName(buildingDto.getWardenName());
		building.setWardenEmail(buildingDto.getWardenEmail());
		building.setWardenContactNo(buildingDto.getWardenContactNo());
        hostel1.setBuildingCount(hostel1.getBuildingCount() + 1);
        hostelRepo.save(hostel1);
		building.setHostel(hostel1);

		try {
			buildingRepo.save(building);
			logger.info("Building saved successfully for hostel id : " + hostelId);
		} catch (BuildingServiceException buildingServiceException) {
			logger.error("error while saving building for hostel id " + hostelId);
			throw new BuildingServiceException("Error while saving building", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public List<GetBuildingDto> getBuildingsByHid(int hostelId) {
		logger.info("getBuildingsByHid method execution has started succesfully for hostel id : " + hostelId);
		Optional<Hostel> hostel = hostelRepo.findById(hostelId);
		logger.info("hostel is founding in database for hostel id : " + hostelId);
		if (hostel.isEmpty()) {
			logger.info("hostel is not found for hostel id : " + hostelId);
			throw new BuildingServiceException("Hostel is not found for this hostel id : " + hostelId,
					HttpStatus.NOT_FOUND);
		}

		logger.info("Hostel is found succesfully for id : " + hostelId);
		Hostel hostel1 = hostel.get();

		List<Building> buildings = buildingRepo.getBuildingsByHid(hostel1.getId());
		logger.info("Buildings are founding in database for hostel id : " + hostelId);
		if (buildings.isEmpty()) {
			logger.warn("Builidngs are not found here for hosteld id : " + hostelId);
			throw new BuildingServiceException("Buildings are not founding for the Hostel Id : " + hostelId,
					HttpStatus.NOT_FOUND);
		}
		
		logger.info("Buildings found succesfully for hostel Id : " + hostelId);
		List<GetBuildingDto> buildings1 = new ArrayList<>();

		for (Building b : buildings) {
			GetBuildingDto buildingDto = new GetBuildingDto();
			buildingDto.setId(b.getId());
			buildingDto.setName(b.getName());
			buildingDto.setFloorCount(b.getFloorCount());
			buildingDto.setWardenName(b.getWardenName());
			buildingDto.setWardenEmail(b.getWardenEmail());
			buildingDto.setWardenContactNo(b.getWardenContactNo());
			
			buildingDto.setHostelName(b.getHostel().getName());
			buildingDto.setOrgnizationName(b.getHostel().getOrgnization().getName());

			buildings1.add(buildingDto);
		}

		logger.info("Buildings fetch succesfully for Hostel id : " + hostelId);
		return buildings1;
	}

	@Override
	public GetBuildingDto getBuildingById(int id) {

		logger.info("getBuilding method execution has started for Id : " + id);
		Optional<Building> building = buildingRepo.findById(id);
		logger.info("building is founding in database for Id : " + id);
		if (building.isEmpty()) {
			logger.info("building is not found for id : " + id);
			throw new BuildingServiceException("Building is not found for this Building id : " + id,
					HttpStatus.NOT_FOUND);
		}

		logger.info("Building is found succesfully for id : " + id);
		Building building1 = building.get();
		GetBuildingDto getBuildingDto = new GetBuildingDto();
		getBuildingDto.setId(building1.getId());
		getBuildingDto.setName(building1.getName());
		getBuildingDto.setFloorCount(building1.getFloorCount());
		getBuildingDto.setWardenName(building1.getWardenName());
		getBuildingDto.setWardenEmail(building1.getWardenEmail());
		getBuildingDto.setWardenContactNo(building1.getWardenContactNo());
		
		getBuildingDto.setHostelName(building1.getHostel().getName());
		getBuildingDto.setOrgnizationName(building1.getHostel().getOrgnization().getName());

		logger.info("Building fetch succesfully");
		return getBuildingDto;
	}

	@Override
	public List<GetBuildingDto> getAllBuildings() {

		logger.info("getAllBuildings method execution has started");

		List<Building> buildings = buildingRepo.findAll();
		if (buildings.isEmpty()) {
			logger.info("Buildings are not present here");
			throw new BuildingServiceException("Buildings are not present", HttpStatus.NOT_FOUND);
		}

		List<GetBuildingDto> getBDtos = new ArrayList<>();

		for (Building b : buildings) {
			GetBuildingDto getBuildingDto = new GetBuildingDto();
			getBuildingDto.setId(b.getId());
			getBuildingDto.setName(b.getName());
			getBuildingDto.setFloorCount(b.getFloorCount());
			getBuildingDto.setWardenName(b.getWardenName());
			getBuildingDto.setWardenEmail(b.getWardenEmail());
			getBuildingDto.setWardenContactNo(b.getWardenContactNo());
			
			getBuildingDto.setHostelName(b.getHostel().getName());
			getBuildingDto.setOrgnizationName(b.getHostel().getOrgnization().getName());

			getBDtos.add(getBuildingDto);

		}
		logger.info("Buildings are found succesfully");

		return getBDtos;

	}

	@Override
	public void deleteBuildingById(int id) {
		logger.info("deleteBuilding method execution has started for Id : " + id);
		Optional<Building> building = buildingRepo.findById(id);
		logger.info("Building is founding in database for id : " + id);

		if (building.isEmpty()) {
			logger.info("Building not found in database for id: ", id);
			throw new BuildingServiceException("Building is not found for this Building id : " + id,
					HttpStatus.NOT_FOUND);
		}

		logger.info("Building has found succesfully for id : " + id);
		Building building1 = building.get();

		buildingRepo.deleteById(building1.getId());
		logger.info("Building has deleted Succesfully");

	}

}
