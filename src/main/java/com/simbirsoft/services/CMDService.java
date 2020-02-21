package com.simbirsoft.services;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.models.Message;
import com.simbirsoft.utils.RoomCommandHandler;
import com.simbirsoft.utils.UserCommandHandler;
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
        if (messageText.startsWith(CMDConst.USER_PREFIX)) {
            new UserCommandHandler(message);
        } else if (messageText.startsWith(CMDConst.ROOM_PREFIX)) {
            new RoomCommandHandler(message);
        }
        ;
    }
}