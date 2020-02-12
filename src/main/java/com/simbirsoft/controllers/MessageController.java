package com.simbirsoft.controllers;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.enumTypes.UserType;
import com.simbirsoft.forms.MessageForm;
import com.simbirsoft.models.Message;
import com.simbirsoft.models.User;
import com.simbirsoft.security.UserDetailsImpl;
import com.simbirsoft.services.CMDService;
import com.simbirsoft.services.MessageService;
import com.simbirsoft.services.RoomService;
import com.simbirsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import static java.lang.String.format;

@Controller
public class MessageController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    private CMDService cmdService;

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable Long roomId, @Payload MessageForm messageForm, Authentication auth) {
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        if (userService.isUserExistInChat(user.getId(), roomId)) {
            messageForm.setFrom(user.getLogin());
            messageForm.setRoomId(roomId);
            Message message = messageService.save(messageForm);
            messagingTemplate.convertAndSend(format("/topic/%s", roomId), message);
        if(message.getText().startsWith(CMDConst.ROOM_PREFIX) | message.getText().startsWith(CMDConst.USER_PREFIX)){
            cmdService.doCommand(message);
        }
        }
    }
}