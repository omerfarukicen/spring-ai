package com.example.spring.ai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageClient;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TutorialController {
    private final ChatClient chatClient;

    private final OpenAiImageClient openAiImageClient;


    @GetMapping
    public String showMainForm(Model model) {
        model.addAttribute("keyword", "");
        model.addAttribute("description","");
        return "index";
    }

    @GetMapping("/textForm")
    public String showForm(Model model) {
        model.addAttribute("keyword", "");
        model.addAttribute("description","");
        return "tutorials";
    }
    @GetMapping("/imageForm")
    public String showImageForm(Model model) {
        model.addAttribute("keyword", "");
        return "imageSearch";
    }
    @GetMapping("/tutorials")
    public String chatOpenAI(Model model, @RequestParam("keyword") String keyword) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("description", chatClient.call(keyword));
        return "tutorials";
    }



    @GetMapping("/imageSearch")
    public String getImageFromOpenAI(Model model, @RequestParam("keyword") String keyword) {
        try {
            ImageResponse response = openAiImageClient.call(
                    new ImagePrompt(keyword,
                            OpenAiImageOptions.builder()
                                    .withQuality("hd")
                                    .withN(4)
                                    .withHeight(1024)
                                    .withWidth(1024)
                                    .build())
            );

            List<String> imageUrls = response.getResults().stream()
                    .map(result -> result.getOutput().getUrl())
                    .collect(Collectors.toList());
            model.addAttribute("imageUrls", imageUrls);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve images: " + e.getMessage());
        }
        return "imageSearch";
    }



}
