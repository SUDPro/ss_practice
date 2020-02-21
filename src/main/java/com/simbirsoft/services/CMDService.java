package com.simbirsoft.services;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.models.Message;
import com.simbirsoft.models.RoomCommand;
import com.simbirsoft.models.UserCommand;
import com.simbirsoft.utils.RoomCommandHandler;
import com.simbirsoft.utils.UserCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CMDService {

    @Autowired
    UserCommandHandler userCommandHandler;

    @Autowired
    RoomCommandHandler roomCommandHandler;

    public void doCommand(Message message) {
        String messageText = message.getText();
        if (messageText.startsWith(CMDConst.USER_PREFIX)) {
            userCommandHandler.chooseCommand(new UserCommand(message));
        } else if (messageText.startsWith(CMDConst.ROOM_PREFIX)) {
            roomCommandHandler.chooseCommand(new RoomCommand(message));
        }
    }
}