package com.example.spring.ai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TutorialFormController {
    @GetMapping
    public String showMainForm(Model model) {
        model.addAttribute("keyword", "");
        model.addAttribute("description", "");
        return "index";
    }

    @GetMapping("/textForm")
    public String showForm(Model model) {
        model.addAttribute("keyword", "");
        model.addAttribute("description", "");
        return "tutorials";
    }

    @GetMapping("/imageForm")
    public String showImageForm(Model model) {
        model.addAttribute("keyword", "");
        return "imageSearch";
    }
}
