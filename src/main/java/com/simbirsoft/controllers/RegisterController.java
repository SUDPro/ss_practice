package com.simbirsoft.controllers;

import com.simbirsoft.forms.UserForm;
import com.simbirsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    private String getRegisterPage(HttpServletRequest request, ModelMap model, Authentication auth) {
        if (auth != null) {
            return "redirect:/home";
        }
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", "Пользователь с таким login уже существует!");
        }
        return "register";
    }

    @PostMapping("/register")
    private String registerUser(UserForm form) {
        if (userService.isUserExist(form.getLogin())) {
            return "redirect:/register?error";
        } else {
            userService.save(form);
            return "redirect:/login";
        }
    }
}