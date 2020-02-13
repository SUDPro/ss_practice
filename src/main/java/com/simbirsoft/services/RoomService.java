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
    private RoomsRepository roomsRepository;

    @Autowired
    private BanInfosRepository banInfosRepository;

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

    public void save(String name, User user, RoomType type) {
        Room room = new Room(name, user, type);
        roomsRepository.save(room);
        addUserToChatList(room, user);
    }

    public List<Room> getAllUsersChats(Long userId) {
        return roomsRepository.findAllByUserId(userId);
    }

    public boolean isRoomExist(String name) {
        return roomsRepository.existsByName(name);
    }

    public User getRoomOwnerByRoomName(String name) {
        return roomsRepository.findByName(name).getOwner();
    }

    public User getRoomOwnerByRoomId(Long id) {
        return roomsRepository.findById(id).get().getOwner();
    }

    public void addUserToChatList(Room room, User user) {
        banInfosRepository.save(new BanInfo(room, user));
    }

    public void removeUserFromChatList(Room room, User user) {
        banInfosRepository.delete(new BanInfo(room, user));
    }

    public void removeRoomByName(String name) {
        roomsRepository.deleteByName(name);
    }

    public void save(Room room) {
        roomsRepository.save(room);
    }
}