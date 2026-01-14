package com.demo.deutschebank.team2project.serviceImpl;

import com.demo.deutschebank.team2project.Entity.Orgnization;
import com.demo.deutschebank.team2project.Repository.OrgnizationRepo;
import com.demo.deutschebank.team2project.dto.AddOrgnizationDTO;
import com.demo.deutschebank.team2project.customexception.OrganizationServiceException;
import com.demo.deutschebank.team2project.dto.GetOrganizationDto;
import com.demo.deutschebank.team2project.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrgaizationServiceImpl implements OrganizationService {

    @Autowired
    OrgnizationRepo organizationRepo;

    @Override
    public void addOrganization(AddOrgnizationDTO orgnizationDto) {
        log.info("Adding a new organization with name: {}", orgnizationDto.getName());
        if (organizationRepo.findByName(orgnizationDto.getName()).isPresent()) {
            log.error("Organization with name {} already exists.", orgnizationDto.getName());
            throw new OrganizationServiceException("Organization with name " + orgnizationDto.getName() + " already exists.", HttpStatus.CONFLICT);
        }
        if (organizationRepo.findByEmail(orgnizationDto.getEmail()).isPresent()) {
            log.error("Organization with email {} already exists.", orgnizationDto.getEmail());
            throw new OrganizationServiceException("Organization with email " + orgnizationDto.getEmail() + " already exists.", HttpStatus.CONFLICT);
        }
        Orgnization orgnization = new Orgnization();
        orgnization.setName(orgnizationDto.getName());
        orgnization.setAddress(orgnizationDto.getAddress());
        orgnization.setComtactNumber(orgnizationDto.getContactNumber());
        orgnization.setEmail(orgnizationDto.getEmail());
        try {
            organizationRepo.save(orgnization);
            log.info("Organization saved successfully with name: {}", orgnizationDto.getName());
        }
        catch (Exception e) {
            log.error("Error while saving organization with name: {}", orgnizationDto.getName(), e);
            throw new OrganizationServiceException("Error while saving organization", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GetOrganizationDto getOrganizationById(int organizationId) {
        log.info("Fetching organization with organizationId: {}", organizationId);
        Orgnization orgnization;
        try{
             orgnization = organizationRepo.findById(organizationId)
                    .orElseThrow(() -> {
                        log.error("Organization with organizationId {} not found.", organizationId);
                        return new OrganizationServiceException("Organization with organizationId " + organizationId + " not found.", HttpStatus.NOT_FOUND);
                    });
        }catch (OrganizationServiceException organizationServiceException){
            throw organizationServiceException;
        }catch (Exception e){
            log.error("Unexpected error fetching organization with id: {}", organizationId, e);
            throw new OrganizationServiceException("Unable to fetch organization.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        GetOrganizationDto orgnizationDTO = new GetOrganizationDto();
        orgnizationDTO.setId(orgnization.getId());
        orgnizationDTO.setName(orgnization.getName());
        orgnizationDTO.setHostelCount(orgnization.getHostelCount());
        orgnizationDTO.setAddress(orgnization.getAddress());
        orgnizationDTO.setContactNumber(orgnization.getComtactNumber());
        orgnizationDTO.setEmail(orgnization.getEmail());
        return orgnizationDTO;
    }

    @Override
    public List<GetOrganizationDto> getAllOrganizations() {
        log.info("Fetching all organizations.");
        try {
            List<Orgnization> orgnizations = organizationRepo.findAll();
            List<GetOrganizationDto> orgnizationDTOList = new ArrayList<>();
            for (Orgnization orgnization : orgnizations) {
                GetOrganizationDto orgnizationDTO = new GetOrganizationDto();
                orgnizationDTO.setId(orgnization.getId());
                orgnizationDTO.setName(orgnization.getName());
                orgnizationDTO.setHostelCount(orgnization.getHostelCount());
                orgnizationDTO.setAddress(orgnization.getAddress());
                orgnizationDTO.setContactNumber(orgnization.getComtactNumber());
                orgnizationDTO.setEmail(orgnization.getEmail());
                orgnizationDTOList.add(orgnizationDTO);
            }
            return orgnizationDTOList;
        } catch (Exception e) {
            log.error("Unexpected error fetching all organizations.", e);
            throw new OrganizationServiceException("Unable to fetch organization list.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteOrganization(int organizationId) {
        log.info("Deleting organization with organizationId:{}", organizationId);
        organizationRepo.findById(organizationId).orElseThrow(()-> {
            log.error("Organization with organizationId {} not found for deletion.", organizationId);
            return new OrganizationServiceException("Organization with organizationId " + organizationId + " not found.", HttpStatus.NOT_FOUND);
        });
        try {
            organizationRepo.deleteById(organizationId);
            log.info("Organization with organizationId {} deleted successfully.", organizationId);
        } catch (Exception e) {
            log.error("Unexpected error deleting organization with id: {}", organizationId, e);
            throw new OrganizationServiceException("Unable to delete organization.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
