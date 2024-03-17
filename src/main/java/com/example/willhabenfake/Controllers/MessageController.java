package com.example.willhabenfake.Controllers;

import com.example.willhabenfake.Models.Message;
import com.example.willhabenfake.Models.MessageDto;
import com.example.willhabenfake.Models.User;
import com.example.willhabenfake.Models.UserDto;
import com.example.willhabenfake.Services.MessageService;
import com.example.willhabenfake.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {
    private MessageService messageService;
    private UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/messages/get-interacted-users")
    public ResponseEntity<?> getInteractedUsers(HttpServletRequest request) {
        Optional<User> currentUser = userService.getCurrentUser(request);
        if (currentUser.isPresent()) {
            List<User> interactedUsers = messageService.getInteractedUsers(currentUser.get());
            for (User user : interactedUsers) {
                System.out.println("User: " + user.getUsername());
            }
            return ResponseEntity.ok().body(UserDto.toUserDtoList(interactedUsers));
        } else {
            return ResponseEntity.internalServerError().body("Error occurred");
        }
    }

    @GetMapping("/messages/get-conversation/{userId}")
    public ResponseEntity<?> getConversationWith(@PathVariable Long userId, HttpServletRequest request) {
        Optional<User> currentUser = userService.getCurrentUser(request);
        Optional<User> otherUser = userService.findUserById(userId);
        if (currentUser.isPresent() && otherUser.isPresent()) {
            List<Message> conversation = messageService.getMessagesBetween(otherUser.get(), currentUser.get());
            List<MessageDto> conversationDto = new ArrayList<>();
            for (Message message : conversation) {
                conversationDto.add(new MessageDto(
                        message.getSender().getId(),
                        message.getReceiver().getId(),
                        message.getContent()));
            }
            return ResponseEntity.ok().body(conversationDto);
        } else {
            return ResponseEntity.internalServerError().body("Error occurred");
        }
    }


    @PostMapping("/messages/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto) {
        Optional<User> sender = userService.findUserById(messageDto.senderId());
        Optional<User> receiver = userService.findUserById(messageDto.receiverId());
        Message message = new Message(sender.get(), receiver.get(), messageDto.content());
        messageService.saveMessage(message);
        return ResponseEntity.ok("Message was sent");
    }


}
