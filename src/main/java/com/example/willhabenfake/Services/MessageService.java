package com.example.willhabenfake.Services;

import com.example.willhabenfake.Models.Message;
import com.example.willhabenfake.Models.User;
import com.example.willhabenfake.Repositories.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;


    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public List<User> getInteractedUsers(User loggedInUser) {
        return messageRepository.findDistinctInteractedUsers(loggedInUser);
    }


    public List<Message> getMessagesBetween(User currentUser, User otherUser) {
        return messageRepository.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(currentUser, otherUser, currentUser, otherUser);
    }
}
