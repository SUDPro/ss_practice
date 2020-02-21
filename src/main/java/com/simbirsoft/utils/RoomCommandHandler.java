package com.simbirsoft.utils;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.enumTypes.RoomType;
import com.simbirsoft.enumTypes.UserType;
import com.simbirsoft.models.BanInfo;
import com.simbirsoft.models.Room;
import com.simbirsoft.models.RoomCommand;
import com.simbirsoft.models.User;
import com.simbirsoft.services.BanInfoService;
import com.simbirsoft.services.RoomService;
import com.simbirsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RoomCommandHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BanInfoService banInfoService;

    public void chooseCommand(RoomCommand command) {
        if (command.getCommand().equals(CMDConst.ROOM_CREATE)) {
            create(command);
        } else if (command.getCommand().equals(CMDConst.ROOM_REMOVE)) {
            remove(command);
        } else if (command.getCommand().equals(CMDConst.ROOM_CONNECT)) {
            connect(command);
        } else if (command.getCommand().equals(CMDConst.ROOM_DISCONNECT)) {
            disconnect(command);
        } else if (command.getCommand().equals(CMDConst.ROOM_RENAME)) {
            rename(command);
        }
    }

    private void rename(RoomCommand command) {
        renameRoom(command.getReceiveRoom(), command.getNewRoomName());
    }

    private void create(RoomCommand command) {
        if (!roomService.isRoomExist(command.getNewRoomName())) {
            if (command.isPrivateRoom()) {
                roomService.save(command.getNewRoomName(), command.getSender(), RoomType.PRIVATE);
            } else {
                roomService.save(command.getNewRoomName(), command.getSender(), RoomType.PUBLIC);
            }
        }
    }

    private void remove(RoomCommand command) {
        if (roomService.isRoomExist(command.getNewRoomName())) {
            if (isUserAdminOrOwner(command.getSender(), roomService.getRoomByName(command.getNewRoomName()))) {
                if (roomService.isRoomExist(command.getNewRoomName())) {
                    roomService.removeRoomByName(command.getNewRoomName());
                }
            }
        }
    }

    private void connect(RoomCommand command) {
        if (!command.getUserLogin().equals("")) {
            if (isUserAndRoomAreExist(command.getUserLogin(), command.getNewRoomName())) {
                if (isUserAdminOrOwner(command.getSender(), command.getReceiveRoom())) {
                    Room room = roomService.getRoomByName(command.getNewRoomName());
                    User user = userService.getUserByLogin(command.getUserLogin());
                    connectUserToChat(room, user);
                }
            }
        } else {
            if (isUserAndRoomAreExist(command.getSender().getLogin(), command.getNewRoomName())) {
                Room room = roomService.getRoomByName(command.getNewRoomName());
                if (!userService.isUserExistInChat(command.getSender().getId(), room.getId())) {
                    if (room.getType().equals(RoomType.PUBLIC)) {
                        banInfoService.save(new BanInfo(room, command.getSender()));
                    }
                }
            }
        }
    }

    private void connectUserToChat(Room room, User user) {
        if (!userService.isUserExistInChat(user.getId(), room.getId())) {
            if (room.getType().equals(RoomType.PRIVATE) &&
                    banInfoService.countUsersInChat(room) < 2) {
                banInfoService.save(new BanInfo(room, user));
            } else if (room.getType().equals(RoomType.PUBLIC)) {
                banInfoService.save(new BanInfo(room, user));
            }
        }
    }

    private void disconnect(RoomCommand command) {
        if (command.getMinutes() != 0) {
            if (isUserAdminOrOwner(command.getSender(), command.getReceiveRoom())) {
                banUserInOneRoom(userService.getUserByLogin(command.getUserLogin()), command.getReceiveRoom(), command.getMinutes());
            }
        } else {
            disconnectUser(command);
        }
    }

    private void banUserInOneRoom(User user, Room room, int minutes) {
        BanInfo banInfo = banInfoService.findBanInfoByUserAndRoom(user, room);
        banInfo.setDateTime(Util.getDatePlusMinutes(minutes));
        banInfoService.save(banInfo);
    }

    private void disconnectUser(RoomCommand command) {
        roomService.removeUserFromChatList(command.getReceiveRoom(), command.getSender());
    }

    private boolean isUserAndRoomAreExist(String login, String roomName) {
        return (userService.isUserExist(login) && roomService.isRoomExist(roomName));
    }

    private boolean isUserAdminOrOwner(User user, Room room) {
        return user.getType().equals(UserType.ADMIN) ||
                roomService.getRoomOwnerByRoomId(room.getId()).getId()
                        .equals(user.getId());
    }

    private void renameRoom(Room room, String newName) {
        room.setName(newName);
        roomService.save(room);
    }
}