package com.simbirsoft.utils;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.models.Message;
import com.simbirsoft.models.User;
import com.simbirsoft.services.BanInfoService;
import com.simbirsoft.services.RoomService;
import com.simbirsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class UserCommandHandler{

    private User sender;
    private String textWithoutPrefix;
    private Message message;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BanInfoService banInfoService;

    public UserCommandHandler(Message message) {
        sender = message.getSender();
        chooseCommand(message);
    }

    private void chooseCommand(Message message){
        textWithoutPrefix = Arrays.toString(message.getText().split(" ", 2));
        if (textWithoutPrefix.startsWith(CMDConst.USER_RENAME)){
            rename(textWithoutPrefix);
        } else if (textWithoutPrefix.startsWith(CMDConst.USER_BAN)){
            ban(textWithoutPrefix);
        } else if (textWithoutPrefix.startsWith(CMDConst.USER_MODERATOR)){
            appointOrDisappointModerator(textWithoutPrefix);
        }
    }

    private void rename(String message){

    }

    private void ban(String message){

    }

    private void appointOrDisappointModerator(String message){

    }
}
