package com.demo.deutschebank.team2project.Repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.deutschebank.team2project.Entity.Bed;

public interface BedRepo extends JpaRepository<Bed, Integer> {
	Optional<Bed> findByBedNoAndRoomId(String bedNo, int roomId);

	int countByRoomId(int roomId);

	List<Bed> findByRoomId(int roomId);
	
//	@Query("select b from Bed b where b.status =:status and b.sharing =:sharing")
//	List<Bed> getVaccantBedBySharing();
	
	@Query("SELECT b FROM Bed b JOIN b.room r WHERE b.status = :status AND r.sharing = :sharing")
	List<Bed> getVacantBedByStatusAndSharing(
	        @Param("status") String status,
	        @Param("sharing") int sharing
	);

	
	
	
}
