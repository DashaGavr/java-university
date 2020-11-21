package com.mmoi.javauniversity.controller;

//import com.mmoi.javauniversity.models.SessionEntity;
//import com.mmoi.javauniversity.repo.SessionEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    //@Autowired
    //private SessionEntityRepository sessionRepository;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Main page");
        return "home";
    }
    @GetMapping("/start")
    public String aboutLAb(Model model) {
        //Iterable<SessionEntity> sessions = sessionRepository.findAll();
        //model.addAttribute("sessions", sessions);
        model.addAttribute("title", "Main page");

        return "home";
    }
}