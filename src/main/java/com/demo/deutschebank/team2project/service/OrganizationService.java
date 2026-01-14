package com.demo.deutschebank.team2project.service;
import com.demo.deutschebank.team2project.dto.AddOrgnizationDTO;
import com.demo.deutschebank.team2project.dto.GetOrganizationDto;

import java.util.List;

public interface OrganizationService {

    void addOrganization(AddOrgnizationDTO orgnizationDTO);

    GetOrganizationDto getOrganizationById(int organizationId);

    List<GetOrganizationDto> getAllOrganizations();

    void deleteOrganization(int organizationId);
}