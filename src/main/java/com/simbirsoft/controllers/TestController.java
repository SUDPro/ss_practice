package com.simbirsoft.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @GetMapping("/test")
    public String mainWithParam(@RequestParam(name = "name", required = false, defaultValue = "") String name,
                                ModelMap map) {
        map.addAttribute("name", name);
        return "test"; //view
    }
}
