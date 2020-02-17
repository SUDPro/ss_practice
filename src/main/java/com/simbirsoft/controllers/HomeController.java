package com.simbirsoft.controllers;

import com.simbirsoft.forms.RoomForm;
import com.simbirsoft.models.User;
import com.simbirsoft.security.UserDetailsImpl;
import com.simbirsoft.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/home")
    private String getUserPage(HttpServletRequest request, ModelMap modelMap, Authentication auth) {
        if (request.getParameterMap().containsKey("error")) {
            modelMap.addAttribute("error", "Ошибка при создании комнаты!");
        }
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("room1", roomService.getRoomByName("room1"));
        modelMap.addAttribute("userRooms", roomService.getAllRoomsByUserId(user.getId()));
        return "user";
    }
    @PostMapping("/home")
    private String addNewRoom(RoomForm form, Authentication auth) {
        form.setName(form.getName().trim());
        if (roomService.isRoomExist(form.getName()) || form.getName().trim().equals("")) {
            return "redirect:/home?error";
        } else {
            roomService.save(form, ((UserDetailsImpl) auth.getPrincipal()).getUser());
            return "redirect:/home";
        }
    }
}