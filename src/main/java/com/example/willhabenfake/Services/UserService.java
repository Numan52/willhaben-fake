package com.example.willhabenfake.Services;

import com.example.willhabenfake.Models.User;
import com.example.willhabenfake.Repositories.ProductRepository;
import com.example.willhabenfake.Repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, ProductRepository productRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getCurrentUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userEmail")) {
                    Long userId = this.findUserByEmail(cookie.getValue()).getId();
                    return findUserById(userId);
                }
            }
        }
        return Optional.empty();
    }

    public boolean validateUserCredentials(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return false;
        } else {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return true;
            }
        }
        return false;
    }

}
