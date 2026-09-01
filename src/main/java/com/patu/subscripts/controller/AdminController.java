package com.patu.subscripts.controller;


import com.patu.subscripts.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;


    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user_list"; 
    }

    @GetMapping("/block/{id}")
    public String blockUser(@PathVariable int id) {
        userService.toggleBlock(id);
        return "redirect:/admin/user_list";
    }
}