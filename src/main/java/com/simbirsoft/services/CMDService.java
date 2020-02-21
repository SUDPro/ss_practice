package com.simbirsoft.services;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.enumTypes.RoomType;
import com.simbirsoft.enumTypes.UserType;
import com.simbirsoft.models.BanInfo;
import com.simbirsoft.models.Message;
import com.simbirsoft.models.Room;
import com.simbirsoft.models.User;
import com.simbirsoft.utils.RoomCommandHandler;
import com.simbirsoft.utils.UserCommandHandler;
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
    private BanInfoService banInfoService;

    public void doCommand(Message message) {
        String messageText = message.getText();
        if(messageText.startsWith(CMDConst.USER_PREFIX)){
            new UserCommandHandler(message);
        } else if(messageText.startsWith(CMDConst.ROOM_PREFIX)){
            new RoomCommandHandler(message);
        };

        String arr[] = message.getText().split("");
        switch (arr[0]) {
            case (CMDConst.ROOM_PREFIX):
                switch (arr[1]) {
                    case (CMDConst.ROOM_CREATE):
                        if (CMDConst.ROOM_CREATE_PARAMETER_PRIVATE.equals(arr[2])) {
                            createRoom(arr[3], message.getSender(), RoomType.PRIVATE);
                        } else {
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
                    case (CMDConst.ROOM_CONNECT):
                        if (arr.length > 3 && CMDConst.PARAMETER_LOGIN.equals(arr[3])) {
                            if (isUserAndRoomAreExist(arr[4], arr[2])) {
                                if (isUserAdminOrOwner(message)) {
                                    Room room = roomService.getRoomByName(arr[2]);
                                    User user = userService.getUserByLogin(arr[4]);
                                    RoomCommandHandler.connectUserToChat(room, user, userService, banInfoService);
                                }
                            }
                        } else {
                            if (isUserAndRoomAreExist(message.getSender().getLogin(), arr[2])) {
                                Room room = roomService.getRoomByName(arr[2]);
                                if (!userService.isUserExistInChat(message.getSender().getId(), room.getId())) {
                                    if (room.getType().equals(RoomType.PUBLIC)) {
                                        banInfoService.save(new BanInfo(room, message.getSender()));
                                    }
                                }
                            }
                        }
                        break;
                    case (CMDConst.ROOM_DISCONNECT):
                        if (arr.length > 2 && CMDConst.PARAMETER_LOGIN.equals(arr[2])) {
                            if (CMDConst.PARAMETER_MINUTES.equals(arr[4])) {
                                if (isUserAdminOrOwnerOrModerator(message)) {
                                    banUserInOneRoom(userService.getUserByLogin(arr[3]), message.getRoom(), arr[5]);
                                }
                            }
                        } else {
                            disconnectUser(message);
                        }
                        break;

                }
                break;
            case (CMDConst.USER_PREFIX):
                switch (arr[1]) {
                    case (CMDConst.USER_RENAME):
                        if (isUserAdminOrOwner(message)) {
                            renameUser(message.getSender(), arr[2]);
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
                        break;
                    case (CMDConst.USER_BAN):
                        if (CMDConst.PARAMETER_LOGIN.equals(arr[2])) {
                            if (CMDConst.PARAMETER_MINUTES.equals(arr[4])) {
                                if (isUserAdminOrOwnerOrModerator(message)) {
                                    banUserInAllRooms(arr[3], arr[5]);
                                }
                            }
                        }
                        break;
                }
                break;
        }
    }

    private void disconnectUser(Message message) {
        roomService.removeUserFromChatList(message.getRoom(), message.getSender());
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    private boolean isUserAndRoomAreExist(String login, String roomName) {
        return (userService.isUserExist(login) && roomService.isRoomExist(roomName));
    }

    private void banUserInOneRoom(User user, Room room, String minutes) {
        if (isInteger(minutes)) {
            BanInfo banInfo = banInfoService.findBanInfoByUserAndRoom(user, room);
            banInfo.setDateTime(Util.getDatePlusMinutes(minutes));
            banInfoService.save(banInfo);
        }
    }

    private void banUserInAllRooms(String login, String minutes) {
        if (isInteger(minutes)) {
            if (userService.isUserExist(login)) {
                User user = userService.getUserByLogin(login);
                for (Room room :
                        roomService.getAllRoomsByUserId(user.getId())) {
                    banUserInOneRoom(user, room, minutes);
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

    private boolean isUserAdminOrOwner(Message message) {
        return message.getSender().getType().equals(UserType.ADMIN) ||
                roomService.getRoomOwnerByRoomId(message.getRoom().getId()).getId()
                        .equals(message.getSender().getId());
    }

    private boolean isUserAdminOrOwnerOrModerator(Message message) {
        return isUserAdminOrOwner(message) || message.getSender().getType().equals(UserType.MODERATOR);
    }

    private void renameUser(User user, String newName) {
        user.setUsername(newName);
        userService.save(user);
    }

    private void renameRoom(Room room, String newName) {
        room.setName(newName);
//        room.setOwner(room.getOwner());
        Room room1 = roomService.save(room);

    }

    private void removeRoom(String name) {
        roomService.removeRoomByName(name);
    }

    private void createRoom(String name, User owner, RoomType type) {
        roomService.save(name, owner, type);
    }
}