package com.greenfoxacademy.springstart.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class FizzBuzzWoof {
    AtomicLong atomicLong = new AtomicLong(0);

    @RequestMapping("/web/greeting4")
    public String greeting2(Model model) {
        int number = (int) atomicLong.getAndIncrement();
        if (number % 3 == 0 && number % 5 == 0 && number % 7 == 0) {
            model.addAttribute("number", "FizzBuzzWoof");
            model.addAttribute("size", 72);
        } else if (number % 3 == 0 && number % 5 == 0) {
            model.addAttribute("number", "FizzBuzz");
            model.addAttribute("size", 48);
        } else if (number % 3 == 0 && number % 7 == 0) {
            model.addAttribute("number", "FizzWoof");
            model.addAttribute("size", 48);
        } else if (number % 5 == 0 && number % 7 == 0) {
            model.addAttribute("number", "BuzzWoof");
            model.addAttribute("size", 48);
        } else if (number % 3 == 0) {
            model.addAttribute("number", "Fizz");
            model.addAttribute("size", 24);
        } else if (number % 5 == 0) {
            model.addAttribute("number", "Buzz");
            model.addAttribute("size", 24);
        } else if (number % 7 == 0) {
            model.addAttribute("number", "Woof");
            model.addAttribute("size", 24);
        } else {
            model.addAttribute("number", number);
            model.addAttribute("size", 16);
        }
        return "greeting4";
    }
}
