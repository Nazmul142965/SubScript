package com.patu.subscripts;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService service;
    private final SubscriptionRepository subscriptionRepository;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("subscriptions", service.getAll());
        model.addAttribute("totalMonthly", service.calculateTotalBurn());
        model.addAttribute("categoryTotals", service.getCategoryTotals());
        return "dashboard";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("subscription", new Subscription());
        return "form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Subscription sub, BindingResult result) {
        if (result.hasErrors()) return "form";
        service.save(sub);
        return "redirect:/";
    }

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
