package com.simbirsoft.controllers;

import com.simbirsoft.models.Message;
import com.simbirsoft.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/api/get-messages")
    @CrossOrigin
    public ResponseEntity getAllMessages(@RequestParam("roomId") Long id) {
        return ResponseEntity.ok(messageService.getMessagesByRoomId(id));
    }
}