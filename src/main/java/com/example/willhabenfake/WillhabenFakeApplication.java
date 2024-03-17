package com.example.willhabenfake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class WillhabenFakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WillhabenFakeApplication.class, args);
    }

}
