package com.demo.deutschebank.team2project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.deutschebank.team2project.Entity.Floor;
import com.demo.deutschebank.team2project.dto.GetFloorDto;

public interface FloorRepo extends JpaRepository<Floor, Integer>{

	
    List<GetFloorDto> findByBuildingId(int id);
    
    @Query("SELECT s FROM Floor s WHERE s.building.id = :building_id")
    public List<Floor> getFloorsByBuildingId(@Param("building_id") int buildingId);

    
      
}
