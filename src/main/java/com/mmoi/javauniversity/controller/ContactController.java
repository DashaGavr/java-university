package com.mmoi.javauniversity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {
    @GetMapping("/contact")
    public String Contact(Model model) {
        return "contact";
    }
    @PostMapping("/contact")
    public String RequestMessage(Model model) {

        return "contact";
    }
}
