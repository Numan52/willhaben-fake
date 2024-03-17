package com.example.willhabenfake.Controllers;

import com.example.willhabenfake.Models.User;
import com.example.willhabenfake.Services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AuthenticationController {
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@ModelAttribute ("user") User user) {
        User existingUser = userService.findUserByEmail(user.getEmail());
        System.out.println(existingUser);
        if (existingUser != null) {
            System.out.println("user exists");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("A user with this email address already exists.");
        } else {
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("User registered successfully. Redirecting to login page.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       HttpServletResponse response) {
        System.out.println(email);
        System.out.println(password);
        boolean correctCredentials = userService.validateUserCredentials(email, password);

        if (correctCredentials) {
            User user = userService.findUserByEmail(email);
            Cookie userCookie = new Cookie("userEmail", email);
            userCookie.setHttpOnly(true);
            userCookie.setSecure(true);
            response.addCookie(userCookie);
            return ResponseEntity.ok(Map.of("username", user.getUsername()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials");
        }

    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Cookie userCookie = new Cookie("userEmail", "");
        userCookie.setMaxAge(0);
        response.addCookie(userCookie);

        request.getSession().invalidate();

        return ResponseEntity.ok("Successfully logged out");
    }


}
