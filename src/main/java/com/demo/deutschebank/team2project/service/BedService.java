package com.demo.deutschebank.team2project.service;

import java.util.List;

import com.demo.deutschebank.team2project.dto.BedDto;
import com.demo.deutschebank.team2project.dto.GetBedDto;

public interface BedService {
	public void addBedByRoomId(int roomId,BedDto bedDto);

	GetBedDto getBedById(int bedId);

	List<GetBedDto> getAllBeds();

	void deleteBedById(int bedId);
	
	List<GetBedDto> getBedsByRoomId(int roomId);
	
	List<GetBedDto> getAvailableBedBySharing(String status,int sharing);

}
