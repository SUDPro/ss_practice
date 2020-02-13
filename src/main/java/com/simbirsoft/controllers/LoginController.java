package com.simbirsoft.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("/login")
    private String getLoginPage(HttpServletRequest request, ModelMap model, Authentication auth) {
        if (auth != null){
            return "redirect:/home";
        }
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", "Неверный логин или пароль!");
        }
        return "login";
    }
}