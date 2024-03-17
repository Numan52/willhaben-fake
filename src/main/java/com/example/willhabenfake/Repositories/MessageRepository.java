package com.example.willhabenfake.Repositories;

import com.example.willhabenfake.Models.Message;
import com.example.willhabenfake.Models.Product;
import com.example.willhabenfake.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(User sender, User receiver, User sender2, User receiver2);

    @Query("SELECT DISTINCT m.sender FROM Message m WHERE m.receiver = :user OR m.sender = :user")
    List<User> findDistinctInteractedUsers(@Param("user") User user);

    @Query("SELECT DISTINCT m.receiver FROM Message m WHERE m.sender = :user OR m.receiver = :user")
    List<User> findDistinctInteractedUsersWithReceiver(@Param("user") User user);
}
