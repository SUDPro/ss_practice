package com.simbirsoft.services;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.enumTypes.RoomType;
import com.simbirsoft.enumTypes.UserType;
import com.simbirsoft.models.BanInfo;
import com.simbirsoft.models.Message;
import com.simbirsoft.models.Room;
import com.simbirsoft.models.User;
import com.simbirsoft.utils.Util;
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

    @Autowired
    private BanInfoService banInfoService;

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
                        if (isUserAdminOrOwner(message)) {
                            removeRoom(arr[2]);
                        }
                        break;
                    case (CMDConst.ROOM_RENAME):
                        if (isUserAdminOrOwner(message)) {
                            renameRoom(message.getRoom(), arr[2]);
                        }
                        break;
                    case(CMDConst.ROOM_ADD_USER):
                        if(!userService.isUserExistInChat(message.getSender().getId(), message.getRoom().getId()))
                        banInfoService.save(new BanInfo(message.getRoom(), message.getSender()));
                        break;
                    case (CMDConst.ROOM_DISCONNECT):

                }
            case (CMDConst.USER_PREFIX):
                switch (arr[1]) {
                    case (CMDConst.USER_RENAME):
                        if (isUserAdminOrOwner(message)) {
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
                                break;
                        }
                    case (CMDConst.USER_BAN):
                        switch (arr[2]) { //arr[3] username
                            case (CMDConst.USER_BAN_PARAMETER_LOGIN):
                                switch (arr[4]) { //arr[5] minutes
                                    case (CMDConst.USER_BAN_PARAMETER_MINUTES):
                                        if (isUserAdminOrOwner(message)) {
                                            banUserInAllRooms(arr[3], arr[5]);
                                        }
                                        break;
                                }
                                break;
                        }
                        break;
                }
        }
    }

    private void banUserInOneRoom(User user, Room room, String minutes) {
        BanInfo banInfo = banInfoService.findBanInfoByUserAndRoom(user, room);
        banInfo.setDateTime(Util.getDatePlusMinutes(minutes));
        banInfoService.save(banInfo);
    }

    private void banUserInAllRooms(String login, String minutes) {
        if (userService.isUserExist(login)) {
            User user = userService.getUserByLogin(login);
            for (Room room :
                    roomService.getAllRoomsByUserId(user.getId())) {
                banUserInOneRoom(user, room, minutes);
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

    private boolean isUserAdminOrOwner(Message message) {
        return message.getSender().getType().equals(UserType.ADMIN) ||
                roomService.getRoomOwnerByRoomId(message.getRoom().getId()).getId()
                        .equals(message.getSender().getId());
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