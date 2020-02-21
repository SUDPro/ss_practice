package com.simbirsoft.utils;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.enumTypes.UserType;
import com.simbirsoft.models.*;
import com.simbirsoft.services.BanInfoService;
import com.simbirsoft.services.RoomService;
import com.simbirsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserCommandHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BanInfoService banInfoService;

    public UserCommandHandler(Message message) {
        chooseCommand(new UserCommand(message));
    }

    private void chooseCommand(UserCommand command) {
        if (command.getCommand().equals(CMDConst.USER_RENAME)) {
            rename(command);
        } else if (command.getCommand().equals(CMDConst.USER_BAN)) {
            ban(command);
        } else if (command.getCommand().equals(CMDConst.USER_MODERATOR)) {
            appointOrDisappointModerator(command);
        }
    }

    private void rename(UserCommand command) {
        if(isUserAdminOrOwner(command)) {
            renameUser(command.getSender(), command.getNewUsername());
        }
    }

    private void ban(UserCommand command) {
        if (command.getSender().getType().equals(UserType.ADMIN)) {
            banUserInAllRooms(command.getLogin(), command.getMinutes());
        }
    }

    private void appointOrDisappointModerator(UserCommand command) {
        if (command.getSender().getType().equals(UserType.ADMIN)) {
            if (command.isPointModerator()) {
                changeRights(UserType.MODERATOR, command.getLogin());
            } else {
                changeRights(UserType.SIMPLE, command.getLogin());
            }
        }
    }

    private void banUserInAllRooms(String login, int minutes) {
        if (userService.isUserExist(login)) {
            User user = userService.getUserByLogin(login);
            for (Room room :
                    roomService.getAllRoomsByUserId(user.getId())) {
                BanInfo banInfo = banInfoService.findBanInfoByUserAndRoom(user, room);
                banInfo.setDateTime(Util.getDatePlusMinutes(minutes));
                banInfoService.save(banInfo);
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

    private boolean isUserAdminOrOwner(UserCommand command) {
        return command.getSender().getType().equals(UserType.ADMIN) ||
                roomService.getRoomOwnerByRoomId(command.getReceiveRoom().getId()).getId()
                        .equals(command.getSender().getId());
    }

    private void renameUser(User user, String newName) {
        user.setUsername(newName);
        userService.save(user);
    }
}
