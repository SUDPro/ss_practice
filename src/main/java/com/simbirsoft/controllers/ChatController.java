package com.simbirsoft.controllers;

import com.simbirsoft.models.User;
import com.simbirsoft.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {

    @GetMapping("/room/{id}")
    private String openChatPage(@PathVariable("id") Long id, ModelMap modelMap, Authentication auth) {
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        modelMap.addAttribute("user", user);
        return "chat";
    }
}