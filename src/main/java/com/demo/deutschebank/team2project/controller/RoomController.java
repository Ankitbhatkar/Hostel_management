package com.demo.deutschebank.team2project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.deutschebank.team2project.dto.BedDto;
import com.demo.deutschebank.team2project.dto.RoomDto;
import com.demo.deutschebank.team2project.service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/addRoomByFloorId/{floorId}")
    ResponseEntity<?> addRoomByFloorId(@PathVariable int floorId, @RequestBody RoomDto roomDto) {
        roomService.addRoom(floorId,roomDto);
        return new ResponseEntity<>("Room added successfully", HttpStatus.CREATED);
    } 

    @GetMapping("/getById/{roomId}")
    ResponseEntity<?> getRoomById(@PathVariable int roomId) {
        return new ResponseEntity<>(roomService.getRoomById(roomId), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    ResponseEntity<?> getAllRooms() {
        return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{roomId}")
    ResponseEntity<String> deleteRoomById(@PathVariable int roomId) {
        roomService.deleteRoomById(roomId);
        return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
    }

    
    @GetMapping("/getRoombyFloorId/{floorId}")
	public ResponseEntity<?> getRoomssByFloorId(@PathVariable int floorId) {
	    return new ResponseEntity<>(roomService.getRoomByFloorId(floorId), HttpStatus.OK);
	}


}
