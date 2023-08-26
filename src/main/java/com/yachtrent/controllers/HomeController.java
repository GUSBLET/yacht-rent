package com.yachtrent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "home/index";
    }
}
