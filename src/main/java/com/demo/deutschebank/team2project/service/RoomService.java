
package com.demo.deutschebank.team2project.service;

import java.util.List;

import com.demo.deutschebank.team2project.dto.GetRoomDto;
import com.demo.deutschebank.team2project.dto.RoomDto;

public interface RoomService {
	void addRoom(int floorId, RoomDto roomDto);

	GetRoomDto getRoomById(int id);

	List<GetRoomDto> getAllRooms();

	void deleteRoomById(int id);

	List<GetRoomDto> getRoomByFloorId(int floorId);
}
