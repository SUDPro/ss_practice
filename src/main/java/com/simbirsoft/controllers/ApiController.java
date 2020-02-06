package com.simbirsoft.controllers;

import com.simbirsoft.models.Message;
import com.simbirsoft.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    MessageService messageService;

    @GetMapping("/api/get-all-messages")
    public List<Message> getAllMessages() {
        return messageService.findAll();
    }
}