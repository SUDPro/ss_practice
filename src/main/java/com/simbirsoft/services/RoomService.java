package com.simbirsoft.services;

import com.simbirsoft.models.Room;
import com.simbirsoft.repositories.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomsRepository roomsRepository;

    public List<Room> getAllRoomsByUserId(Long id) {
        return roomsRepository.findAllByUserId(id);
    }
}