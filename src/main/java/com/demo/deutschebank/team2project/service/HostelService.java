package com.demo.deutschebank.team2project.service;

import java.util.List;

import com.demo.deutschebank.team2project.dto.GetHostelDto;
import com.demo.deutschebank.team2project.dto.HostelDto;

public interface HostelService {

	public void addHostel(int organizationId, HostelDto hostelDto);

	public GetHostelDto getHostelById(int hostelId);

	public List<GetHostelDto> getAllHostels();

	public void deleteHostelById(int hostelId);

	public List<GetHostelDto> getByOrganizationId(int id);

    List<GetHostelDto> filterHostel(String area, String city);
}
