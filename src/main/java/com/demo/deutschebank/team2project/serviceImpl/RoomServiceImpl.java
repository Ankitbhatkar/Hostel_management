package com.demo.deutschebank.team2project.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.deutschebank.team2project.Entity.Floor;
import com.demo.deutschebank.team2project.Entity.Room;
import com.demo.deutschebank.team2project.Repository.FloorRepo;
import com.demo.deutschebank.team2project.Repository.RoomRepo;
import com.demo.deutschebank.team2project.customexception.BedServiceException;
import com.demo.deutschebank.team2project.customexception.RoomServiceException;
import com.demo.deutschebank.team2project.dto.GetRoomDto;
import com.demo.deutschebank.team2project.dto.RoomDto;
import com.demo.deutschebank.team2project.service.RoomService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepo roomRepo;

	@Autowired
	private FloorRepo floorRepo;

	@Override
	public void addRoom(int floorId, RoomDto roomDto) {
		log.info("Adding room: {}", roomDto);

		Floor floor = floorRepo.findById(floorId).orElseThrow(() -> {
			log.warn("Floor not found with ID: {}", floorId);
			return new RoomServiceException("Floor with id " + floorId + " not found.", HttpStatus.NOT_FOUND);
		});

		if (roomRepo.findByRoomNoAndFloorId(roomDto.getRoomNo(), floorId).isPresent()) {
			log.warn("Room no {} already exists on floor {}", roomDto.getRoomNo(), floorId);
			throw new RoomServiceException("Room no " + roomDto.getRoomNo() + " already exists on this floor.",
					HttpStatus.CONFLICT);
		}

		int existingRooms = roomRepo.countRoomsByFloorId(floorId);
		if (existingRooms >= floor.getRoomCount()) {
			log.warn("Cannot add room. Floor {} capacity full. Max rooms = {}", floor.getFloorNo(),
					floor.getRoomCount());
			throw new RoomServiceException("Cannot add room. Floor " + floor.getFloorNo() + " is full.",
					HttpStatus.BAD_REQUEST);
		}

		Room room = new Room();
		room.setRoomNo(roomDto.getRoomNo());
		room.setSharing(roomDto.getSharing());
		room.setType(roomDto.getType());
		room.setFloor(floor);

		try {
			roomRepo.save(room);
			log.info("Room saved successfully. RoomNo: {}, FloorId: {}", roomDto.getRoomNo(), floorId);
		} catch (Exception e) {
			log.error("Error while saving room: {}", e.getMessage());
			throw new RoomServiceException("Error while saving room.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public GetRoomDto getRoomById(int id) {
		log.info("Fetching room by ID: {}", id);

		Room room = roomRepo.findById(id).orElseThrow(() -> {
			log.error("Room not found with id {}", id);
			return new RoomServiceException("Room not found with id: " + id, HttpStatus.NOT_FOUND);
		});

		GetRoomDto dto = new GetRoomDto();
		dto.setId(room.getId());
		dto.setRoomNo(room.getRoomNo());
		dto.setSharing(room.getSharing());
		dto.setType(room.getType());
		dto.setFloorName(room.getFloor().getFloorNo() + "");
		dto.setBuildingName(room.getFloor().getBuilding().getName());
		dto.setHostelName(room.getFloor().getBuilding().getHostel().getName());
		dto.setOrgName(room.getFloor().getBuilding().getHostel().getOrgnization().getName());

		log.info("Room found: RoomNo={}, FloorId={}", room.getRoomNo(), room.getFloor().getId());
		return dto;
	}

	@Override
	public List<GetRoomDto> getAllRooms() {
		log.info("Fetching all rooms");

		List<Room> rooms = roomRepo.findAll();
		List<GetRoomDto> roomDtos = new ArrayList<>();

		for (Room room : rooms) {
			GetRoomDto dto = new GetRoomDto();
			dto.setId(room.getId());
			dto.setRoomNo(room.getRoomNo());
			dto.setSharing(room.getSharing());
			dto.setType(room.getType());
			dto.setFloorName(room.getFloor().getFloorNo() + "");
			dto.setBuildingName(room.getFloor().getBuilding().getName());
			dto.setHostelName(room.getFloor().getBuilding().getHostel().getName());
			dto.setOrgName(room.getFloor().getBuilding().getHostel().getOrgnization().getName());
			roomDtos.add(dto);
		}

		log.info("Total rooms fetched: {}", roomDtos.size());
		return roomDtos;
	}

	@Override
	public void deleteRoomById(int id) {
		log.info("Deleting room with ID: {}", id);

		Room room = roomRepo.findById(id).orElseThrow(() -> {
			log.error("Room not found with id {}", id);
			return new RoomServiceException("Room not found with id: " + id, HttpStatus.NOT_FOUND);
		});

		try {
			roomRepo.delete(room);
			log.info("Room deleted successfully: RoomNo={}, FloorId={}", room.getRoomNo(), room.getFloor().getId());
		} catch (Exception e) {
			log.error("Error while deleting room with ID: {}", id, e);
			throw new RoomServiceException("Error while deleting room with id: " + id,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<GetRoomDto> getRoomByFloorId(int floorId) {
		log.info("Fetching rooms for floorId: {}", floorId);

		List<Room> rooms = roomRepo.findByFloorId(floorId);

		if (rooms == null || rooms.isEmpty()) {
			log.warn("No rooms found for floorId: {}", floorId);
			throw new BedServiceException("No rooms found for floorId: " + floorId, HttpStatus.NOT_FOUND);
		}

		List<GetRoomDto> roomDtos = new ArrayList<>();
		for (Room room : rooms) {
			GetRoomDto dto = new GetRoomDto();
			dto.setId(room.getId());
			dto.setRoomNo(room.getRoomNo());
			dto.setSharing(room.getSharing());
			dto.setType(room.getType());
			dto.setFloorName(room.getFloor().getFloorNo() + "");
			dto.setBuildingName(room.getFloor().getBuilding().getName());
			dto.setHostelName(room.getFloor().getBuilding().getHostel().getName());
			dto.setOrgName(room.getFloor().getBuilding().getHostel().getOrgnization().getName());
			roomDtos.add(dto);
		}

		log.info("Total rooms found for floorId {}: {}", floorId, roomDtos.size());
		return roomDtos;
	}
}