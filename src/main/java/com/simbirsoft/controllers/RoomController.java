package com.simbirsoft.controllers;

import com.simbirsoft.enumTypes.RoomType;
import com.simbirsoft.models.User;
import com.simbirsoft.security.UserDetailsImpl;
import com.simbirsoft.services.BanInfoService;
import com.simbirsoft.services.MessageService;
import com.simbirsoft.services.RoomService;
import com.simbirsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Controller
public class RoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BanInfoService banInfoService;

    @GetMapping("/room/{id}")
    private String openChatPage(@PathVariable("id") Long id, ModelMap modelMap, Authentication auth) {
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        if (userService.getUserByRoomId(user.getId(), id) != null) {
            if (banInfoService.findBanInfoByUserAndRoom(user, roomService.getRoomById(id)).getDateTime().before(new Date())) {
                if (roomService.getRoomById(id).getType().equals(RoomType.PRIVATE) &&
                        userService.isUserExistInChat(user.getId(), id)) {
                    modelMap.addAttribute("user", user);
                    modelMap.addAttribute("messages", messageService.getMessagesByRoomId(id));
                    return "chat";
                } else if (roomService.getRoomById(id).getType().equals(RoomType.PUBLIC)) {
                    modelMap.addAttribute("user", user);
                    modelMap.addAttribute("messages", messageService.getMessagesByRoomId(id));
                }
            }
        }
        return "redirect:/home";
    }
}