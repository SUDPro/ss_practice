package com.simbirsoft.controllers;

import com.simbirsoft.forms.MessageForm;
import com.simbirsoft.models.Room;
import com.simbirsoft.models.User;
import com.simbirsoft.security.UserDetailsImpl;
import com.simbirsoft.services.RoomService;
import com.simbirsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.Date;

import static java.lang.String.format;

@Controller
public class MessageController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable Long roomId, @Payload MessageForm message, Authentication auth) {
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        if (userService.isUserExistInChat(user.getId(), roomId)){
            message.setFrom(user.getUsername());
            messagingTemplate.convertAndSend(format("/topic/%s", roomId), message);
        }
    }

    @MessageMapping("/chat/{roomId}/addUser")
    public void addUserToChat(@DestinationVariable Long roomId, @Payload MessageForm message, Authentication auth) {
       
    }

}