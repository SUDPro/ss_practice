package com.simbirsoft.services;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.enumTypes.RoomType;
import com.simbirsoft.enumTypes.UserType;
import com.simbirsoft.models.Message;
import com.simbirsoft.models.Room;
import com.simbirsoft.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CMDService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private MessageService messageService;

    public void doCommand(Message message) {
        String arr[] = message.getText().split(" ");
        switch (arr[0]) {
            case (CMDConst.ROOM_PREFIX):
                switch (arr[1]) {
                    case (CMDConst.ROOM_CREATE):
                        switch (arr[2]) {
                            case (CMDConst.ROOM_CREATE_PARAMETER_PRIVATE):
                                createRoom(arr[3], message.getSender(), RoomType.PRIVATE);
                                break;
                            default:
                                createRoom(arr[2], message.getSender(), RoomType.PUBLIC);
                        }
                        break;
                    case (CMDConst.ROOM_REMOVE):
                        if (message.getSender().getType().equals(UserType.ADMIN) ||
                                roomService.getRoomOwnerByRoomId(message.getRoom().getId()).getId()
                                        .equals(message.getSender().getId())) {
                            removeRoom(arr[2]);
                        }
                        break;
                    case (CMDConst.ROOM_RENAME):
                        if (message.getSender().getType().equals(UserType.ADMIN) ||
                                roomService.getRoomOwnerByRoomId(message.getRoom().getId()).getId()
                                        .equals(message.getSender().getId())) {
                            renameRoom(message.getRoom(), arr[2]);
                        }
                        break;
                    case (CMDConst.ROOM_DISCONNECT):

                }
            case (CMDConst.USER_PREFIX):
                switch (arr[1]) {
                    case (CMDConst.USER_RENAME):
                        if (message.getSender().getType().equals(UserType.ADMIN) ||
                                roomService.getRoomOwnerByRoomId(message.getRoom().getId()).getId()
                                        .equals(message.getSender().getId())) {
                            renameUser(message.getSender(), arr[3]);
                        }
                        break;
                    case (CMDConst.USER_MODERATOR):
                        switch (arr[2]) {
                            case (CMDConst.USER_MODERATOR_ADD):
                                changeRights(UserType.MODERATOR, arr[3]);
                                break;
                            case (CMDConst.USER_MODERATOR_DELETE):
                                changeRights(UserType.SIMPLE, arr[3]);
                        }
                }
        }
    }

    private void changeRights(UserType moderator, String login) {
        User user = userService.getUserByLogin(login);
        if (user.getType() != UserType.ADMIN) {
            user.setType(moderator);
            userService.save(user);
        }
    }

    private void renameUser(User user, String newName) {
        user.setUsername(newName);
        userService.save(user);
    }

    private void renameRoom(Room room, String newName) {
        room.setName(newName);
        roomService.save(room);
    }

    private void removeRoom(String name) {
        roomService.removeRoomByName(name);
    }

    private void createRoom(String name, User owner, RoomType type) {
        roomService.save(name, owner, type);
    }

}