package com.demo.deutschebank.team2project.service;

import java.util.List;

import com.demo.deutschebank.team2project.dto.BuildingDto;
import com.demo.deutschebank.team2project.dto.GetBuildingDto;

public interface BuildingService {
	public void addBuildingByHid(BuildingDto buildingDto, int hostelId);

	public List<GetBuildingDto> getBuildingsByHid(int hId);

	public GetBuildingDto getBuildingById(int id);

	public List<GetBuildingDto> getAllBuildings();

	public void deleteBuildingById(int id);

}
