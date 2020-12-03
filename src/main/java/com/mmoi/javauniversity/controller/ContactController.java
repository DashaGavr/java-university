package com.mmoi.javauniversity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.internet.MimeMessage;

@Controller
public class ContactController {

    public String text;

    public String mycredentials = "mmip.msu@gmail.com";

    @GetMapping("/contact")
    public String Contact(Model model) {
        model.addAttribute("title", "Contact");
        return "contact";
    }

    @Autowired
    private JavaMailSender sender;

    @PostMapping("/contact")
    public String home(@RequestParam String credentials, @RequestParam String text) {
        try {
            System.out.println(credentials);
            System.out.println(text);
            sendEmail(credentials, text);
            return "/contact";
        }
        catch (Exception ex) {
            return "Error in sending email: "+ex;
        }
    }

    private void sendEmail(String credentials, String text) throws Exception{
        MimeMessage message = sender.createMimeMessage();
        // Enable the multipart flag!
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setTo(mycredentials);
        helper.setText("From user: " + credentials + System.lineSeparator() + text);
        helper.setSubject("MMIP:response from site");
        sender.send(message);
    }
}

