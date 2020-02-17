package com.simbirsoft.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    private String redirectToHome(){
        return "redirect:/home";
    }
}
