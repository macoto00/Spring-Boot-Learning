package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/post")
    public String createPost() {
        return "createPost.html";
    }
}
