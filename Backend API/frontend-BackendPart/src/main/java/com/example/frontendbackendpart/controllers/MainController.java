package com.example.frontendbackendpart.controllers;

import com.example.frontendbackendpart.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/doubling")
    public ResponseEntity<?> doubling(@RequestParam(required = false) String input) {

        if (input == null || input.isEmpty()) {
            ErrorDto errorDto = new ErrorDto("Please provide an input!");
            return ResponseEntity.badRequest().body(errorDto);
        }

        int value = Integer.parseInt(input);

        int result = value * 2;
        DoublingDto doublingDto = new DoublingDto(value, result);

        return ResponseEntity.ok(doublingDto);
    }

    @GetMapping("/greeter")
    public ResponseEntity<?> greeter(@RequestParam(required = false) String name, @RequestParam(required = false) String title) {
        if ((name == null || name.isEmpty()) && (title == null || title.isEmpty())) {
            return ResponseEntity.badRequest().body(new ErrorDto("Please provide a name and a title!"));
        } else if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorDto("Please provide a name!"));
        } else if (title == null || title.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorDto("Please provide a title!"));
        } else {
            return ResponseEntity.ok(new Person(name, title));

            // or I can use this code:
            // String welcomeMessage = "Oh, hi there " + name + ", my dear " + title + "!";
            // GreetingDto greetingDto = new GreetingDto(welcomeMessage);
            // return ResponseEntity.ok(greetingDto);
        }
    }

    @GetMapping("/appenda/{appendable}")
    public ResponseEntity<?> appendA(@PathVariable String appendable) {
        if (appendable != null) {
            return ResponseEntity.ok(new AppendA(appendable + "a"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/dountil/{operation}")
    public ResponseEntity<?> doUntil(@PathVariable String operation, @RequestBody Map<String, Integer> request) {
        Integer number = request.get("until");
        int result = 0;

        if (number == null) {
            return ResponseEntity.badRequest().body(new ErrorDto("Please provide a number!"));
        } else if (operation.equals("sum")) {
            for (int i = 1; i <= number; i++) {
                result += i;
            }
            return ResponseEntity.ok(new DoUntil(result));
        } else if (operation.equals("factor")) {
            result = 1;
            for (int i = 2; i <= number; i++) {
                result *= i;
            }
            return ResponseEntity.ok(new DoUntil(result));
        }

        return ResponseEntity.badRequest().body(new ErrorDto("Invalid operation!"));
    }
}
