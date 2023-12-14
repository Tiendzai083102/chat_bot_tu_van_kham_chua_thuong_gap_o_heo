package com.codeweb.springjdbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "index"; 
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam("message") String message, Model model) {
        // Xử lý tin nhắn ở đây và lấy câu trả lời từ backend
        String reply = "Câu trả lời từ backend";

        // Gửi câu trả lời về giao diện
        model.addAttribute("reply", reply);

        return "index"; // Trở về trang giao diện với câu trả lời
    }
}