package com.example.willhabenfake.Controllers;

import com.example.willhabenfake.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @GetMapping("/post-ad")
    public String getPostAd() {
        return "post-ad";
    }

    @GetMapping("/post-new-ad")
    public String getPostNewAd() {
        return "post-new-ad";
    }

    @GetMapping("/product-details")
    public String getProductDetails() {
        return "product-details";
    }

    @GetMapping("/messages/get-messages")
    public String getMessages() {
        return "messages";
    }

    @GetMapping("/my-profile")
    public String getMyProfile(HttpServletRequest request) {
        if (userService.getCurrentUser(request).isPresent()) {
            return "profile";
        } else {
            return "redirect:/login";
        }


    }

}
