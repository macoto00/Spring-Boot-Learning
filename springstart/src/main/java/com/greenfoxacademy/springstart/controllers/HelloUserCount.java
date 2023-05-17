package com.greenfoxacademy.springstart.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class HelloUserCount {
    AtomicLong atomicLong = new AtomicLong(0);
    @RequestMapping("/web/greeting2")
    public String greeting2(Model model, @RequestParam String value) {
        model.addAttribute("name", value);
        model.addAttribute("number", atomicLong.getAndIncrement());
        return "greeting2";
    }
}
