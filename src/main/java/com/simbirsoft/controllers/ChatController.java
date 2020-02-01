package com.simbirsoft.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping("/room")
    public String openChatPage() {
        return "chat";
    }
}
