package com.patu.subscripts.controller;


import com.patu.subscripts.model.User;
import com.patu.subscripts.repository.UserRepository;
import com.patu.subscripts.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final UserService userService;


    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user_list";
    }

    @GetMapping("/block/{id}")
    public String handleBlock(@PathVariable int id, Principal principal) {
        String loggedInEmail = principal.getName();


        User admin = userRepository.findByEmail(loggedInEmail).get();


        if (admin.getId() == id) {
            return "redirect:/admin/users?error=selfblock";
        }
        userService.toggleUserBlock(id);

        return "redirect:/admin/users";
    }
}