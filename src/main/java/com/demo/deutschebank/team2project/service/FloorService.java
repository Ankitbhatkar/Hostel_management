package com.demo.deutschebank.team2project.service;

import com.demo.deutschebank.team2project.dto.FloorDto;
import com.demo.deutschebank.team2project.dto.GetFloorDto;

import java.util.List;

public interface FloorService {
	void addFloor(int buildingId, FloorDto floorDto);

	List<GetFloorDto> getFloorByBuildingId(int id);

	GetFloorDto getFloorById(int id);

	List<GetFloorDto> getAllFloors();

	void deleteFloorById(int id);
}