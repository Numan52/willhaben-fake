package com.example.willhabenfake.Controllers;

import com.example.willhabenfake.Models.Product;
import com.example.willhabenfake.Models.User;
import com.example.willhabenfake.Services.MessageService;
import com.example.willhabenfake.Services.ProductService;
import com.example.willhabenfake.Services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    private MessageService messageService;
    private UserService userService;
    private ProductService productService;

    public UserController(MessageService messageService, UserService userService, ProductService productService) {
        this.messageService = messageService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/users/get-userId-by-productId")
    public ResponseEntity<?> getUserIdByProductId(@RequestParam("productId") Long productId){
        Map<String, Long> json = new HashMap<>();
        Optional<Product> product = productService.findProductById(productId);
        Long ownerId = null;
        if (product.isPresent()) {
            ownerId = product.get().getUser().getId();
            json.put("userId", ownerId);
            return ResponseEntity.ok(json);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/get-current-userId")
    public ResponseEntity<?> getCurrentUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userEmail")) {
                    Long userId = userService.findUserByEmail(cookie.getValue()).getId();
                    return ResponseEntity.ok(Map.of("userId", userId));
                }
            }
        }
        return ResponseEntity.internalServerError().body("An error occurred. Try again later.");

    }


}
