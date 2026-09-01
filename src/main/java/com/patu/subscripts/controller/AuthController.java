package com.patu.subscripts.controller;

import com.patu.subscripts.dto.UserRegistrationDTO;
import com.patu.subscripts.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;


    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("userDto", new UserRegistrationDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userdto") UserRegistrationDTO dto, BindingResult result)
    {
        if(result.hasErrors()) {
            return "auth/register";
        }
        userService.saveUser(dto);
        return "redirect:/login";
    }

}
