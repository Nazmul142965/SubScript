package com.patu.subscripts.controller;

import com.patu.subscripts.model.User;
import com.patu.subscripts.repository.UserRepository;
import com.patu.subscripts.services.SubscriptionService;
import com.patu.subscripts.model.Subscription;
import com.patu.subscripts.repository.SubscriptionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService service;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String dashboard(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).get();

        List<Subscription> subList;
        if (user.getRole().equals("ROLE_ADMIN")) {
            subList = subscriptionRepository.findAll();
            model.addAttribute("isAdmin", true);
            model.addAttribute("totalUsers", userRepository.count());
        } else {
            subList = subscriptionRepository.findAllByUser(user);
            model.addAttribute("isAdmin", false);
        }

        model.addAttribute("subscriptions", subList);
        model.addAttribute("username", user.getUsername());


        model.addAttribute("totalMonthly", service.calculateTotalForList(subList));
        model.addAttribute("categoryTotals", service.getCategoryTotalsForList(subList));
        model.addAttribute("mostExpensive", service.getMostExpensiveName(subList));

        return "dashboard";
    }
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("subscription", new Subscription());
        return "form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Subscription sub, BindingResult result, Principal principal) {
        if (result.hasErrors()) return "form";

        String email = principal.getName();

        User loggedInUser = userRepository.findByEmail(email).get();

        sub.setUser(loggedInUser);
        service.save(sub);

        return "redirect:/";
    };


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("subscription", service.getById(id));
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/";
    }
    @GetMapping("/timeline")
    public String timeline(Model model) {
        model.addAttribute("subscriptions", service.getAll());
        return "timeline";
    }

}
