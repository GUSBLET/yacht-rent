package com.yachtrent.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("home/index")
    public String index(Model model){
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "home/index";
    }
}
