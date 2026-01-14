package com.demo.deutschebank.team2project.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.deutschebank.team2project.Entity.Hostel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HostelRepo extends JpaRepository<Hostel, Integer>{
	   Optional<Hostel> findByName(String name);
	   Optional<Hostel> findByEmail(String email);
       List<Hostel> findByOrgnization_Id(int id);

    @Query("SELECT h from Hostel h WHERE h.address IS NOT NULL AND " +
            "(:area IS NULL OR lower(h.address.area) LIKE :area) AND " +
            "(:city IS NULL OR lower(h.address.city) LIKE :city)")
    List<Hostel> filterHostel(@Param("area") String area, @Param("city") String city);
}
