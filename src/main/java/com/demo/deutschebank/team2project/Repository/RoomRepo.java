package com.demo.deutschebank.team2project.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.deutschebank.team2project.Entity.Room;


@Repository
public interface RoomRepo extends JpaRepository<Room, Integer> {
	Optional<Room> findByRoomNoAndFloorId(int roomNo, int flooId);

	@Query("SELECT COUNT(r) FROM Room r WHERE r.floor.id = :floorId")
	int countRoomsByFloorId(int floorId);

	List<Room> findByFloorId(int floorId);
}

