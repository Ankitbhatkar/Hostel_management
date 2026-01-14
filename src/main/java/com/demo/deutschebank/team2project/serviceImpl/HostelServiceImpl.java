package com.demo.deutschebank.team2project.serviceImpl;

import com.demo.deutschebank.team2project.Entity.Address;
import com.demo.deutschebank.team2project.Repository.AddressRepo;
import com.demo.deutschebank.team2project.dto.GetHostelDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.deutschebank.team2project.Entity.Hostel;
import com.demo.deutschebank.team2project.Entity.Orgnization;
import com.demo.deutschebank.team2project.Repository.HostelRepo;
import com.demo.deutschebank.team2project.Repository.OrgnizationRepo;
import com.demo.deutschebank.team2project.customexception.HostelServiceException;
import com.demo.deutschebank.team2project.dto.HostelDto;
import com.demo.deutschebank.team2project.service.HostelService;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HostelServiceImpl implements HostelService {

	@Autowired
	private HostelRepo hostelRepo;

	@Autowired
	private OrgnizationRepo orgRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Override
    public void addHostel(int organizationId, HostelDto hostelDto) {

        log.info("addHostel method is called with orgId: {} and data: {}", organizationId, hostelDto);

        log.info("Checking organization with ID: {}", organizationId);

        Orgnization org = orgRepo.findById(organizationId).orElse(null);
        if (org == null) {
            log.warn("Organization not found with ID: {}", organizationId);
            throw new HostelServiceException(
                    "Organization not found with ID: " + organizationId,
                    HttpStatus.NOT_FOUND
            );
        }

        log.info("Organization found: {}", org.getName());

        log.info("Checking duplicate hostel name: {}", hostelDto.getName());
        hostelRepo.findByName(hostelDto.getName()).ifPresent(h -> {
            throw new HostelServiceException(
                    "Hostel with name " + hostelDto.getName() + " already exists.",
                    HttpStatus.CONFLICT
            );
        });

        log.info("Checking duplicate hostel email: {}", hostelDto.getEmail());
        hostelRepo.findByEmail(hostelDto.getEmail()).ifPresent(h -> {
            throw new HostelServiceException(
                    "Hostel with email " + hostelDto.getEmail() + " already exists.",
                    HttpStatus.CONFLICT
            );
        });

        log.info("Creating and saving address");

        Address address = new Address();
        address.setArea(hostelDto.getAddress().getArea());
        address.setCity(hostelDto.getAddress().getCity());
        address.setZipCode(hostelDto.getAddress().getZipCode());
        address.setState(hostelDto.getAddress().getState());

        try {
            addressRepo.save(address);
            log.info("Address saved successfully with ID: {}", address.getId());
        } catch (Exception e) {
            log.error("Error while saving address: {}", e.getMessage());
            throw new HostelServiceException(
                    "Error while saving address",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        Hostel hostel = new Hostel();
        hostel.setName(hostelDto.getName());
        hostel.setEmail(hostelDto.getEmail());
        hostel.setContactNumber(hostelDto.getContactNumber());
        hostel.setCapacity(hostelDto.getCapacity());
        hostel.setType(hostelDto.getType());
        hostel.setImage(hostelDto.getImage());
        hostel.setWebsite(hostelDto.getWebsite());
        hostel.setLatitude(hostelDto.getLatitude());
        hostel.setLongitude(hostelDto.getLongitude());
        hostel.setAddress(address);
        hostel.setOrgnization(org);

        org.setHostelCount(org.getHostelCount() + 1);
        orgRepo.save(org);

        try {
            log.info("Saving hostel: {}", hostel.getName());
            hostelRepo.save(hostel);
            log.info("Hostel saved successfully: {}", hostel.getName());
        } catch (Exception e) {
            log.error("Error while saving hostel: {}", e.getMessage());
            throw new HostelServiceException(
                    "Error while saving hostel",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

	@Override
	public GetHostelDto getHostelById(int hostelId) {

		log.info("getHostelById method is called with ID: {}", hostelId);

		Hostel hostel = hostelRepo.findById(hostelId).orElse(null);
		if (hostel == null) {
			log.info("Hostel not found with ID: {}", hostelId);
			throw new HostelServiceException("Hostel not found with ID: " + hostelId, HttpStatus.NOT_FOUND);
		}
        GetHostelDto getHostelDto = new GetHostelDto();
        getHostelDto.setId(hostel.getId());
        getHostelDto.setName(hostel.getName());
        getHostelDto.setEmail(hostel.getEmail());
        getHostelDto.setCapacity(hostel.getCapacity());
        getHostelDto.setType(hostel.getType());
        getHostelDto.setArea(hostel.getAddress().getArea());
        getHostelDto.setCity(hostel.getAddress().getCity());
        getHostelDto.setZipCode(hostel.getAddress().getZipCode());
        getHostelDto.setState(hostel.getAddress().getState());
        getHostelDto.setCountry(hostel.getAddress().getCountry());
        getHostelDto.setContactNumber(hostel.getContactNumber());
        getHostelDto.setImage(hostel.getImage());
        getHostelDto.setWebsite(hostel.getWebsite());
        getHostelDto.setBuildingCount(hostel.getBuildingCount());
        getHostelDto.setLatitude(hostel.getLatitude());
        getHostelDto.setLongitude(hostel.getLongitude());
       
		return getHostelDto;
	}

	@Override
	public List<GetHostelDto> getAllHostels() {

	    log.info("getAllHostels method is called");

	    List<Hostel> hostelList = hostelRepo.findAll();
	    log.info("Total hostels found: {}", hostelList.size());

	    List<GetHostelDto> dtoList = new ArrayList<>();

	    for (Hostel hostel : hostelList) {
	        GetHostelDto dto = new GetHostelDto();
	        dto.setId(hostel.getId());
	        dto.setName(hostel.getName());
	        dto.setEmail(hostel.getEmail());
	        dto.setCapacity(hostel.getCapacity());
	        dto.setType(hostel.getType());
	        dto.setContactNumber(hostel.getContactNumber());
	        dto.setImage(hostel.getImage());
	        dto.setWebsite(hostel.getWebsite());
	        dto.setBuildingCount(hostel.getBuildingCount());
	        dto.setLatitude(hostel.getLatitude());
	        dto.setLongitude(hostel.getLongitude());
	            dto.setArea(hostel.getAddress().getArea());
	            dto.setCity(hostel.getAddress().getCity());
	            dto.setZipCode(hostel.getAddress().getZipCode());
	            dto.setState(hostel.getAddress().getState());
	            dto.setCountry(hostel.getAddress().getCountry());
	        

	        dtoList.add(dto);
	    }

	    return dtoList;
	}
	
	
	
	
	
	@Override
	public void deleteHostelById(int hostelId) {

		log.info("deleteHostelById method is called with ID: {}", hostelId);

		Hostel hostel = hostelRepo.findById(hostelId).orElse(null);
		if (hostel == null) {
			log.info("Hostel not found with ID: {}", hostelId);
			throw new HostelServiceException("Hostel not found with ID: " + hostelId, HttpStatus.NOT_FOUND);
		}

		hostelRepo.delete(hostel);
		log.info("Hostel deleted successfully with ID: {}", hostelId);
		
	}

	@Override
	public List<GetHostelDto> getByOrganizationId(int id) {

	    Optional<Orgnization> organization = orgRepo.findById(id);

	    if (organization.isEmpty()) {
	        throw new HostelServiceException("Organization not found with id: " + id,
	                HttpStatus.NOT_FOUND);
	    }

	    List<Hostel> hostels = hostelRepo.findByOrgnization_Id(id);

	    List<GetHostelDto> dtoList = new ArrayList<>();

	    for (Hostel hostel : hostels) {
	        GetHostelDto dto = new GetHostelDto();

	        dto.setId(hostel.getId());
	        dto.setName(hostel.getName());
	        dto.setEmail(hostel.getEmail());
	        dto.setCapacity(hostel.getCapacity());
	        dto.setType(hostel.getType());
	        dto.setContactNumber(hostel.getContactNumber());
	        dto.setImage(hostel.getImage());
	        dto.setWebsite(hostel.getWebsite());
	        dto.setBuildingCount(hostel.getBuildingCount());
	        dto.setLatitude(hostel.getLatitude());
	        dto.setLongitude(hostel.getLongitude());

	     
	            dto.setArea(hostel.getAddress().getArea());
	            dto.setCity(hostel.getAddress().getCity());
	            dto.setZipCode(hostel.getAddress().getZipCode());
	            dto.setState(hostel.getAddress().getState());
	            dto.setCountry(hostel.getAddress().getCountry());
	        

	        dtoList.add(dto);
	    }

	    return dtoList;
	}


    @Override
    public List<GetHostelDto> filterHostel(String area, String city) {
        if (area != null && !area.trim().isEmpty()) {
            area = "%" + area.trim().toLowerCase() + "%";
        } else {
            area = null;
        }
        if (city != null && !city.trim().isEmpty()) {
            city = "%" + city.trim().toLowerCase() + "%";
        } else {
            city = null;
        }
        log.info("Filtering hostels by area: {} and city: {}", area, city);
        try {
            List<Hostel> hostels = hostelRepo.filterHostel(area, city);
            if (hostels.isEmpty()) {
                log.warn("No hostels found for area: {} and city: {}", area, city);
                throw new HostelServiceException("No hostels found for the given criteria", HttpStatus.NOT_FOUND);
            }
            log.info("Found {} hostels for area: {} and city: {}", hostels.size(), area, city);
            List<GetHostelDto> hostelDtos = new ArrayList<>();
            for (Hostel hostel : hostels) {
                GetHostelDto getHostelDto = new GetHostelDto();
                getHostelDto.setId(hostel.getId());
                getHostelDto.setName(hostel.getName());
                getHostelDto.setEmail(hostel.getEmail());
                getHostelDto.setCapacity(hostel.getCapacity());
                getHostelDto.setType(hostel.getType());
                getHostelDto.setArea(hostel.getAddress().getArea());
                getHostelDto.setCity(hostel.getAddress().getCity());
                getHostelDto.setZipCode(hostel.getAddress().getZipCode());
                getHostelDto.setState(hostel.getAddress().getState());
                getHostelDto.setCountry(hostel.getAddress().getCountry());
                getHostelDto.setContactNumber(hostel.getContactNumber());
                getHostelDto.setImage(hostel.getImage());
                getHostelDto.setWebsite(hostel.getWebsite());
                getHostelDto.setBuildingCount(hostel.getBuildingCount());
                getHostelDto.setLatitude(hostel.getLatitude());
                getHostelDto.setLongitude(hostel.getLongitude());
                hostelDtos.add(getHostelDto);
            }
            log.info("Successfully mapped {} hostels to DTOs", hostelDtos.size());
            return hostelDtos;
        } catch (Exception e) {
            log.error("Error filtering hostels for area: {} and city: {}", area, city, e);
            throw new HostelServiceException("An error occurred while filtering hostels", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
