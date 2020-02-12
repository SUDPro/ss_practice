package com.simbirsoft.services;

import com.simbirsoft.enumTypes.RoomType;
import com.simbirsoft.forms.RoomForm;
import com.simbirsoft.models.BanInfo;
import com.simbirsoft.models.Room;
import com.simbirsoft.models.User;
import com.simbirsoft.repositories.BanInfosRepository;
import com.simbirsoft.repositories.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomsRepository roomsRepository;

    @Autowired
    BanInfosRepository banInfosRepository;

    public List<Room> getAllRoomsByUserId(Long id) {
        return roomsRepository.findAllByUserId(id);
    }

    public Room getRoomById(Long id) {
        return roomsRepository.getOne(id);
    }

    public void save(RoomForm form, User user) {
        Room room = new Room(form.getName(), user, RoomType.PUBLIC);
        if (form.getType()) {
            room.setType(RoomType.PRIVATE);
        }
        roomsRepository.save(room);
        addUserToChatList(room, user);
    }

    public boolean isRoomExist(String name) {
        return roomsRepository.existsByName(name);
    }

    public void addUserToChatList(Room room, User user) {
        banInfosRepository.save(new BanInfo(room, user));
    }
}