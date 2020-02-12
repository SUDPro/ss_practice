package com.simbirsoft.controllers;

import com.simbirsoft.models.User;
import com.simbirsoft.security.UserDetailsImpl;
import com.simbirsoft.services.RoomService;
import com.simbirsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @GetMapping("/user")
    private String getUserPage(ModelMap modelMap, Authentication auth) {
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("rooms", roomService.getAllRoomsByUserId(user.getId()));
        return "user";
    }
}