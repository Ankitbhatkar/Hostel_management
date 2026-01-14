package com.demo.deutschebank.team2project.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.deutschebank.team2project.Entity.Bed;
import com.demo.deutschebank.team2project.Entity.Room;
import com.demo.deutschebank.team2project.Repository.BedRepo;
import com.demo.deutschebank.team2project.Repository.RoomRepo;
import com.demo.deutschebank.team2project.customexception.BedServiceException;
import com.demo.deutschebank.team2project.dto.BedDto;
import com.demo.deutschebank.team2project.dto.GetBedDto;
import com.demo.deutschebank.team2project.service.BedService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BedServiceImpl implements BedService {

	@Autowired
	BedRepo bedRepo;

	@Autowired
	RoomRepo roomRepo;

	@Override
	public void addBedByRoomId(int roomId, BedDto bedDto) {

		log.info("Adding bed: {}", bedDto);

		Room room = roomRepo.findById(roomId).orElseThrow(() -> {
			log.warn("Room not found with ID: {}", roomId);
			return new BedServiceException("Room with id " + roomId + " not found.", HttpStatus.NOT_FOUND);
		});

		if (bedRepo.findByBedNoAndRoomId(bedDto.getBedNo(), roomId).isPresent()) {
			log.warn("Bed no {} already exists in room {}", bedDto.getBedNo(), roomId);
			throw new BedServiceException("Bed no " + bedDto.getBedNo() + " already exists in this room.",
					HttpStatus.CONFLICT);
		}

		int existingBeds = bedRepo.countByRoomId(roomId);
		if (existingBeds >= room.getSharing()) {
			log.warn("Room {} capacity full. Sharing: {}, Existing Beds: {}", roomId, room.getSharing(), existingBeds);

			throw new BedServiceException("Cannot add more beds. Room sharing capacity full.", HttpStatus.BAD_REQUEST);
		}

		Bed bed = new Bed();
		bed.setBedNo(bedDto.getBedNo());
		bed.setStatus(bedDto.getStatus());
		bed.setPrice(bedDto.getPrice());
		bed.setDeposit(bedDto.getDeposit());
		bed.setRoom(room);

		try {
			bedRepo.save(bed);
			log.info("Bed saved successfully. BedNo: {}, RoomId: {}", bedDto.getBedNo(), roomId);
		} catch (Exception e) {
			log.error("Error while saving bed: {}", e.getMessage());
			throw new BedServiceException("Error while saving bed.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public GetBedDto getBedById(int bedId) {

		log.info("Fetching bed with ID: {}", bedId);

		Bed bed = bedRepo.findById(bedId)
				.orElseThrow(() -> new BedServiceException("Bed not found with ID: " + bedId, HttpStatus.NOT_FOUND));
		
		GetBedDto getBedDto = new GetBedDto();
		getBedDto.setId(bed.getId());
		getBedDto.setBedNo(bed.getBedNo());
		getBedDto.setStatus(bed.getStatus());
		getBedDto.setPrice(bed.getPrice());
		getBedDto.setDeposit(bed.getDeposit());
		getBedDto.setRoomId(bed.getId());
		getBedDto.setFloorNo(bed.getRoom().getRoomNo() + "");
		getBedDto.setBuildingName(bed.getRoom().getFloor().getBuilding().getName());
		getBedDto.setHostelName(bed.getRoom().getFloor().getBuilding().getHostel().getName());
		getBedDto.setOrgName(bed.getRoom().getFloor().getBuilding().getHostel().getOrgnization().getName());
		log.info("Bed found successfully for Bed ID: {}", bedId);

		return getBedDto;

	}

	@Override
	public List<GetBedDto> getAllBeds() {
		log.info("Fetching all beds");
		try {
			List<Bed> beds = bedRepo.findAll();
			List<GetBedDto> bedDtoList = new ArrayList<>();
			for (Bed bed : beds) {
				GetBedDto getBedDto = new GetBedDto();
				getBedDto.setId(bed.getId());
				getBedDto.setBedNo(bed.getBedNo());
				getBedDto.setStatus(bed.getStatus());
				getBedDto.setPrice(bed.getPrice());
				getBedDto.setDeposit(bed.getDeposit());
				getBedDto.setRoomId(bed.getId());
				getBedDto.setFloorNo(bed.getRoom().getRoomNo() + "");
				getBedDto.setBuildingName(bed.getRoom().getFloor().getBuilding().getName());
				getBedDto.setHostelName(bed.getRoom().getFloor().getBuilding().getHostel().getName());
				getBedDto.setOrgName(bed.getRoom().getFloor().getBuilding().getHostel().getOrgnization().getName());
				bedDtoList.add(getBedDto);
			}
			log.info("Total beds found: {}", bedDtoList.size());
			return bedDtoList;
		} catch (Exception e) {
			log.error("Unexpected error fetching all beds.", e);
			throw new BedServiceException("Unable to fetch bed list.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void deleteBedById(int bedId) {

		log.info("Deleting bed with ID: {}", bedId);

		if (!bedRepo.existsById(bedId)) {
			log.warn("Bed not found with ID: {}", bedId);
			throw new BedServiceException("Bed not found with ID: " + bedId, HttpStatus.NOT_FOUND);
		}

		bedRepo.deleteById(bedId);
		log.info("Bed deleted successfully with ID: {}", bedId);
	}

	@Override
	public List<GetBedDto> getBedsByRoomId(int roomId) {

		log.info("Fetching beds for roomId: {}", roomId);

		try {
			List<Bed> beds = bedRepo.findByRoomId(roomId);

			if (beds == null || beds.isEmpty()) {
				log.warn("No beds found for roomId: {}", roomId);
				throw new BedServiceException("No beds found for roomId: " + roomId, HttpStatus.NOT_FOUND);
			}

			List<GetBedDto> dtoList = new ArrayList<>();

			for (Bed bed : beds) {
				GetBedDto getBedDto = new GetBedDto();
				getBedDto.setId(bed.getId());
				getBedDto.setBedNo(bed.getBedNo());
				getBedDto.setStatus(bed.getStatus());
				getBedDto.setPrice(bed.getPrice());
				getBedDto.setDeposit(bed.getDeposit());
				getBedDto.setRoomId(bed.getId());
				getBedDto.setFloorNo(bed.getRoom().getRoomNo() + "");
				getBedDto.setBuildingName(bed.getRoom().getFloor().getBuilding().getName());
				getBedDto.setHostelName(bed.getRoom().getFloor().getBuilding().getHostel().getName());
				getBedDto.setOrgName(bed.getRoom().getFloor().getBuilding().getHostel().getOrgnization().getName());
				dtoList.add(getBedDto);
			}

			log.info("Total beds found for roomId {}: {}", roomId, dtoList.size());
			return dtoList;

		} catch (BedServiceException ex) {
			throw ex;

		} catch (Exception e) {
			log.error("Unexpected error while fetching beds for roomId: {}", roomId, e);
			throw new BedServiceException("Unable to fetch beds for room.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Override
	public List<GetBedDto> getAvailableBedBySharing(String status, int sharing) {
		log.info("Fetching vacant beds where status is  : {}", status,"sharing is : {} ",sharing);
			
		try {
			List<Bed> beds = bedRepo.getVacantBedByStatusAndSharing(status, sharing);
			List<GetBedDto> bedDtoList = new ArrayList<>();
			for (Bed bed : beds) {
				GetBedDto getBedDto = new GetBedDto();
				getBedDto.setId(bed.getId());
				getBedDto.setBedNo(bed.getBedNo());
				getBedDto.setStatus(bed.getStatus());
				getBedDto.setPrice(bed.getPrice());
				getBedDto.setDeposit(bed.getDeposit());
				getBedDto.setRoomId(bed.getId());
				getBedDto.setFloorNo(bed.getRoom().getRoomNo() + "");
				getBedDto.setBuildingName(bed.getRoom().getFloor().getBuilding().getName());
				getBedDto.setHostelName(bed.getRoom().getFloor().getBuilding().getHostel().getName());
				getBedDto.setOrgName(bed.getRoom().getFloor().getBuilding().getHostel().getOrgnization().getName());
				bedDtoList.add(getBedDto);
			}
			log.info("Total beds found: {}", bedDtoList.size());
			return bedDtoList;
		} catch (Exception e) {
			log.error("Unexpected error fetching all beds.", e);
			throw new BedServiceException("Unable to fetch bed list.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
