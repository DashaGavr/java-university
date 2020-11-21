package com.mmoi.javauniversity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutLabController {
    @GetMapping("/aboutLab")
    public String aboutLab(Model model) {
        return "about-lab";
    }
}
