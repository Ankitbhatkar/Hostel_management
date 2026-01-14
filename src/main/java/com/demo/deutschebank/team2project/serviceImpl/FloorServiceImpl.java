
package com.demo.deutschebank.team2project.serviceImpl;

import com.demo.deutschebank.team2project.dto.GetFloorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.deutschebank.team2project.Entity.Building;
import com.demo.deutschebank.team2project.Entity.Floor;
import com.demo.deutschebank.team2project.Repository.BuildingRepo;
import com.demo.deutschebank.team2project.Repository.FloorRepo;
import com.demo.deutschebank.team2project.customexception.FloorServiceException;
import com.demo.deutschebank.team2project.dto.FloorDto;
import com.demo.deutschebank.team2project.service.FloorService;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class FloorServiceImpl implements FloorService {

	@Autowired
	FloorRepo floorRepo;

	@Autowired
	BuildingRepo buildingRepo;

	public void addFloor(int buildingId, FloorDto floorDto) {
		log.info("Entering addFloor method for building ID: {}", buildingId);
		Building building = buildingRepo.findById(buildingId)
				.orElseThrow(() -> new FloorServiceException("Building id not found", HttpStatus.NOT_FOUND));
		
		Floor floor = new Floor();
		floor.setFloorNo(floorDto.getFloorNo());
		floor.setRoomCount(floorDto.getRoomCount());
        building.setFloorCount(building.getFloorCount() + 1);
        buildingRepo.save(building);
		floor.setBuilding(building);
        try {
            floorRepo.save(floor);
            log.info("Floor added successfully for building ID: {}", buildingId);
        }
        catch (FloorServiceException floorServiceException) {
            log.error("Error while saving floor for building ID: {}", buildingId, floorServiceException);
            throw new FloorServiceException("Error while saving floor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

    @Override
    public GetFloorDto getFloorById(int id) {
        log.info("Entering getFloorById method for floor ID: {}", id);
        Floor floor=floorRepo.findById(id).orElseThrow(() ->
                new FloorServiceException("Floor not found", HttpStatus.NOT_FOUND));
        GetFloorDto getFloorDto = new GetFloorDto();
        getFloorDto.setId(floor.getId());
        getFloorDto.setFloorNo(floor.getFloorNo());
        getFloorDto.setRoomCount(floor.getRoomCount());
        getFloorDto.setBuildingName(floor.getBuilding().getName());
        getFloorDto.setHostelName(floor.getBuilding().getHostel().getName());
        getFloorDto.setOrgName(floor.getBuilding().getHostel().getOrgnization().getName());
        log.info("Floor found successfully for floor ID: {}", id);
        return getFloorDto;
    }

    @Override
    public List<GetFloorDto> getFloorByBuildingId(int id) {
        log.info("Entering getFloorByBuildingId method for building ID: {}", id);
        Building building = buildingRepo.findById(id).orElseThrow(() ->
                new FloorServiceException("Building id not found", HttpStatus.NOT_FOUND));

        List<Floor> floors = building.getFloors();
        List<GetFloorDto> floorDtos = new ArrayList<>();
        for (Floor floor : floors) {
            GetFloorDto getFloorDto = new GetFloorDto();
            getFloorDto.setId(floor.getId());
            getFloorDto.setFloorNo(floor.getFloorNo());
            getFloorDto.setRoomCount(floor.getRoomCount());
            getFloorDto.setBuildingName(floor.getBuilding().getName());
            getFloorDto.setHostelName(floor.getBuilding().getHostel().getName());
            getFloorDto.setOrgName(floor.getBuilding().getHostel().getOrgnization().getName());
            floorDtos.add(getFloorDto);
        }
        log.info("Found {} floors for building ID: {}", floorDtos.size(), id);
        return floorDtos;
    }



    @Override
    public List<GetFloorDto> getAllFloors() {
        log.info("Entering getAllFloors method");
        List<Floor> floors = floorRepo.findAll();
        List<GetFloorDto> floorDtos = new ArrayList<>();
        for (Floor floor : floors) {
            GetFloorDto getFloorDto = new GetFloorDto();
            getFloorDto.setId(floor.getId());
            getFloorDto.setFloorNo(floor.getFloorNo());
            getFloorDto.setRoomCount(floor.getRoomCount());
            getFloorDto.setBuildingName(floor.getBuilding().getName());
            getFloorDto.setHostelName(floor.getBuilding().getHostel().getName());
            getFloorDto.setOrgName(floor.getBuilding().getHostel().getOrgnization().getName());
            floorDtos.add(getFloorDto);
        }
        log.info("Retrieved {} floors", floorDtos.size());
        return floorDtos;
    }

    @Override
    @Transactional
    public void deleteFloorById(int id) {
        log.info("Entering deleteFloorById method for floor ID: {}", id);
        Floor floor =floorRepo.findById(id)
                .orElseThrow(() -> new FloorServiceException("Floor not found", HttpStatus.NOT_FOUND));
        floorRepo.delete(floor);
        log.info("Floor with ID: {} deleted successfully", id);

    }


}